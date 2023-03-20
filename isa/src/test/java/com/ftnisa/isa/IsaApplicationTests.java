package com.ftnisa.isa;

import com.ftnisa.isa.dto.auth.LoginRequest;
import com.ftnisa.isa.dto.auth.RegisterRequest;
import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.RoleRepository;
import com.ftnisa.isa.repository.TokenRepository;
import com.ftnisa.isa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IsaApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String username = "john.smith";
    private static final String password = "pass";
    private static final String passwordHash = "$2a$12$s0F2SVOE0nNh8la8P6pQDulIJ9R5.DO8qUSNn/nH4qeG.9QfnMxry";
    private static final String firstname = "John";
    private static final String lastname = "Smith";
    private static final String email = "john.smith@isa-uber.com";
    private static final String requestEmail = "pera.peric@isa-uber.com";
    private static final String requestUsername = "pera.peric";
    private static final String requestFirstname = "Pera";
    private static final String requestLastname = "Peric";
    private static final RegisterRequest registerRequest =
            new RegisterRequest(requestUsername, password, requestEmail, requestFirstname, requestLastname);
    private static final RegisterRequest registerRequestExisting =
            new RegisterRequest(username, password, email, requestFirstname, requestLastname);

    @BeforeEach
    public void setup() {
        tokenRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        var role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
        roleRepository.save(role);

        var existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setPassword(passwordHash);
        existingUser.setEmail(email);
        existingUser.setFirstname(firstname);
        existingUser.setLastname(lastname);
        existingUser.setEnabled(true);
        userRepository.save(existingUser);
    }

    @Test
    public void signInCorrectDataShouldReturnToken() {
        ResponseEntity<String> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/signin",
                new LoginRequest(username, password), String.class);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        String responseBody = response.getBody();
        assertThat(responseBody).isNotEmpty();
    }

    @Test
    public void signupUserCorrectDataShouldReturnRegisterResponse() {
        ResponseEntity<UserResponse> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/signup/user",
                registerRequest, UserResponse.class);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        UserResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(requestUsername, responseBody.getUsername());
        assertEquals(requestFirstname, responseBody.getFirstname());
        assertEquals(requestLastname, responseBody.getLastname());
    }

    @Test
    public void signupUserExistingUsernameShouldReturnConflict() throws Exception {
        ResponseEntity<Object> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/signup/user",
                registerRequestExisting, Object.class);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode().value());
        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertInstanceOf(LinkedHashMap.class, responseBody);
        assertThat(((LinkedHashMap<?, ?>) responseBody).get("message"))
                .isEqualTo("Username already exists!");
    }
}