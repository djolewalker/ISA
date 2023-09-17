package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.auth.RegisterRequest;
import com.ftnisa.isa.dto.user.CreateDriverRequest;
import com.ftnisa.isa.dto.user.DriverChangeRequestDto;
import com.ftnisa.isa.dto.user.UserRequest;
import com.ftnisa.isa.event.resetPasswordRequested.OnResetPasswordRequestedEvent;
import com.ftnisa.isa.event.verificationRequested.OnVerificationRequestedEvent;
import com.ftnisa.isa.exception.ResourceConflictException;
import com.ftnisa.isa.mapper.UserMapper;
import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.token.TokenType;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.DriverChangeRequest;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.Vehicle;
import com.ftnisa.isa.model.vehicle.VehicleType;
import com.ftnisa.isa.repository.*;

import lombok.AllArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final UserMapper userMapper;
    private final DriverChangeRequestRepository driverChangeRequestRepository;
    private final NotificationService notificationService;
    private final PanicRepository panicRepository;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegisterRequest registerRequest, String role) {
        var userCheck = userRepository.findByUsername(registerRequest.getUsername());
        if (userCheck != null) {
            throw new ResourceConflictException(userCheck.getId(), "Username already exists!");
        }

        var user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        List<Role> roles = roleService.findByName(role);
        if (roles.isEmpty()) {
            throw new NotFoundException("Role not found");
        }

        user.setRoles(roles);
        user.setEnabled(false);

        var createdUser = this.userRepository.save(user);

        eventPublisher.publishEvent(new OnVerificationRequestedEvent(createdUser));

        return createdUser;
    }

    @Override
    public void verify(String verificationToken) {
        var token = tokenService.findToken(verificationToken, TokenType.VERIFICATION);
        var user = token.getUser();

        user.setEnabled(true);
        userRepository.save(user);

        tokenService.removeToken(token);
    }

    @Override
    public void forgotPassword(String email) {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with email '" + email + "' not found!");
        }

        eventPublisher.publishEvent(new OnResetPasswordRequestedEvent(user));
    }

    @Override
    public void resetPassword(String password, String resetPasswordToken) {
        var token = tokenService.findToken(resetPasswordToken, TokenType.RESET_PASSWORD);
        var user = token.getUser();

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        tokenService.removeToken(token);
    }

    @Override
    public User updateUserProfile(String username, UserRequest userRequest) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        updateUserFields(user, userRequest);
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(int id, UserRequest userRequest) {
        var user = userRepository.findById(id).orElseThrow();

        updateUserFields(user, userRequest);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public Driver registerDriver(CreateDriverRequest driverRequest) {
        var userCheck = userRepository.findByUsername(driverRequest.getUsername());
        if (userCheck != null) {
            throw new ResourceConflictException(userCheck.getId(), "Username already exists!");
        }

        var vehicleRequest = driverRequest.getVehicle();
        var vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleTypeRepository.findById(vehicleRequest.getVehicleTypeId()).orElseThrow());
        vehicle.setVehicleModel(vehicleRequest.getVehicleModel());
        vehicle.setBabyFriendly(vehicleRequest.isBabyFriendly());
        vehicle.setPetFriendly(vehicleRequest.isPetFriendly());
        vehicle.setNumberOfSeats(vehicleRequest.getNumberOfSeats());
        vehicle.setRegistrationNumber(vehicleRequest.getRegistrationNumber());
        vehicleRepository.save(vehicle);

        var driver = new Driver();
        updateUserFields(driver, driverRequest);
        driver.setEmail(driverRequest.getEmail());
        driver.setUsername(driverRequest.getUsername());
        driver.setPassword(passwordEncoder.encode(driverRequest.getPassword()));
        driver.setRoles(roleService.findByName(Role.DRIVER));
        driver.setEnabled(true);

        driver.setDriverLicense(driverRequest.getDriverLicense());
        driver.setVehicle(vehicle);

        driverRepository.save(driver);

        return driver;
    }

    private void updateUserFields(User user, UserRequest userRequest) {
        user.setAddress(userRequest.getAddress());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setPhone(userRequest.getPhone());
        user.setImage(userRequest.getImage());
        user.setAddress(userRequest.getAddress());
    }

    @Override
    @Transactional
    public void createDriverChangeRequest(DriverChangeRequestDto driverChangeRequestDto) {
        DriverChangeRequest driverChangeRequest = userMapper
                .driverChangeRequestDtoToDriverChangeRequest(driverChangeRequestDto);
        User driver = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        driverChangeRequest.setDriverId(driver.getId());
        driverChangeRequestRepository.save(driverChangeRequest);
    }

    @Override
    @Transactional
    public Driver resolveDriverChangeRequest(Integer driverChangeRequestId, boolean isApproved) {

        DriverChangeRequest driverChangeRequest = driverChangeRequestRepository.findById(driverChangeRequestId)
                .orElseThrow();
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverRepository.findById(driverChangeRequest.getDriverId()).orElseThrow();

        if (!isApproved){
            driverChangeRequest.setApproved(false);
            driverChangeRequest.setResolvedBy(admin);
            driverChangeRequest.setResolveTime(LocalDateTime.now());
            driverChangeRequestRepository.save(driverChangeRequest);
            notificationService.createInstantNotification(driver, "Vaš zahtev za izmenu podataka na profilu je odbijen.");
            return driver;
        }

        Vehicle vehicle = driver.getVehicle();
        VehicleType vehicleType = vehicleTypeRepository.findById(driverChangeRequest.getVehicleTypeId()).orElseThrow();

        driver.setUsername(driverChangeRequest.getUsername());
        driver.setEmail(driverChangeRequest.getEmail());
        driver.setFirstname(driverChangeRequest.getFirstname());
        driver.setLastname(driverChangeRequest.getLastname());
        driver.setImage(driverChangeRequest.getImage());
        driver.setPhone(driverChangeRequest.getPhone());
        driver.setAddress(driverChangeRequest.getAddress());
        driver.setDriverLicense(driverChangeRequest.getDriverLicense());

        vehicle.setVehicleModel(driverChangeRequest.getVehicleModel());
        vehicle.setRegistrationNumber(driverChangeRequest.getRegistrationNumber());
        vehicle.setNumberOfSeats(driverChangeRequest.getNumberOfSeats());
        vehicle.setBabyFriendly(driverChangeRequest.isBabyFriendly());
        vehicle.setPetFriendly(driverChangeRequest.isPetFriendly());
        vehicle.setVehicleType(vehicleType);

        driverChangeRequest.setApproved(true);
        driverChangeRequest.setResolvedBy(admin);
        driverChangeRequest.setResolveTime(LocalDateTime.now());
        notificationService.createInstantNotification(driver, "Vaš zahtev za izmenu podataka na profilu je odobren.");

        driverRepository.save(driver);
        vehicleRepository.save(vehicle);
        driverChangeRequestRepository.save(driverChangeRequest);

        return driver;
    }

    @Override
    @Transactional
    public Panic resolvePanic(Integer panicId) {
        Panic panic = panicRepository.findById(panicId).orElseThrow();
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        panic.setResolved(true);
        panic.setResolvedBy(admin);
        panic.setResolveTime(LocalDateTime.now());

        panicRepository.save(panic);

        return panic;
    }
}
