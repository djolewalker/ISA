package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.reports.DayStatistics;
import com.ftnisa.isa.dto.reports.Report;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.ride.RideStatus;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.DriverRepository;
import com.ftnisa.isa.repository.RideRepository;
import com.ftnisa.isa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public Report getIndividualAdminReport(LocalDate startDate, LocalDate endDate, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return generateReport(startDate, endDate, user);
    }

    @Override
    @Transactional
    public Report getReport(LocalDate startDate, LocalDate endDate) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return generateReport(startDate, endDate, user);
    }


    private Report generateReport(LocalDate startDate, LocalDate endDate, User user){

        int userType = determineUserType(user);
        if (userType == 0){
            return null;
        }

        Report report = new Report();
        report.setStartDate(startDate);
        report.setEndDate(endDate);

        int totalRides = 0;
        float totalKilometers = 0;
        int totalMoney = 0;
        List<DayStatistics> statisticsList = new ArrayList<>();

        var dates = startDate.datesUntil(endDate.plusDays(1)).toList();
        for (LocalDate d : dates){
            var rides = getAllRides(d, user);
            var numOfRides = rides != null ? rides.size() : 0;
            var kilometers = getKilometers(rides);

            var money = getMoney(rides);
            DayStatistics dayStatistics = new DayStatistics(d, numOfRides, kilometers, money);
            totalRides = totalRides + numOfRides;
            totalKilometers = totalKilometers + kilometers;
            totalMoney = totalMoney + money;
            statisticsList.add(dayStatistics);
        }

        report.setStatisticsList(statisticsList);

        report.setTotalRides(totalRides);
        report.setTotalKilometers(totalKilometers);
        report.setTotalMoney(totalMoney);

        report.setAvgRides(totalRides/dates.size());
        report.setAvgKilometers(totalKilometers/dates.size());
        report.setAvgMoney(totalMoney/dates.size());

        return report;
    }

    private int getMoney(List<Ride> rides) {
        if (rides == null){
            return 0;
        }
        int money = 0;
        for (Ride r : rides){
            money = money + (int) r.getTotalPrice();
        }
        return money;
    }

    private float getKilometers(List<Ride> rides) {
        if (rides == null){
            return 0;
        }
        var total = rides.stream()
                .filter(ride -> ride.getRoutes().size() > 0)
                .map(ride -> ride.getRoutes().get(0).getLength())
                .reduce(Float::sum);
        return total.isPresent() ? total.get() : 0;
    }

    private int determineUserType(User user){
        var roles= user.getRoles();
        var roleIds = roles.stream().map(Role::getId).toList();
        if (roleIds.contains(3)){
            return 3;
        } else if (roleIds.contains(2)) {
            return 2;
        } else if (roleIds.contains(1)) {
            return 1;
        } else {
            return 0;
        }
    }


    private List<Ride> getAllRides(LocalDate date, User user){
        var userType = determineUserType(user);
        if (userType==1){
            return getAllRidesForDateForUser(date, user);
        }
        if (userType==2){
            return getAllRidesForDateForDriver(date, user);
        }
        if (userType==3){
            return getAllRidesForDateForAdmin(date);
        }
        return null;
    }

    private List<Ride> getAllRidesForDateForUser(LocalDate date, User user){
        List<Ride> rides = rideRepository.findByPassengerAndStartTimeBetweenAndRideStatus(
                user,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay(),
                RideStatus.FINISHED
        );
        return rides;
    }

    private List<Ride> getAllRidesForDateForDriver(LocalDate date, User user){
        Driver driver = driverRepository.findById(user.getId()).orElseThrow();
        List<Ride> rides = rideRepository.findByDriverAndStartTimeBetweenAndRideStatus(
                driver,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay(),
                RideStatus.FINISHED
        );
        return rides;
    }

    private List<Ride> getAllRidesForDateForAdmin(LocalDate date){
        List<Ride> rides = rideRepository.findByStartTimeBetweenAndRideStatus(
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay(),
                RideStatus.FINISHED
        );
        return rides;
    }


}
