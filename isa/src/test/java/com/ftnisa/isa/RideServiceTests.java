package com.ftnisa.isa;


import com.ftnisa.isa.repository.UserRepository;
import com.ftnisa.isa.service.RideService;
import com.ftnisa.isa.service.RideServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;





@SpringBootTest
public class RideServiceTests {

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUp(){

    }

    @AfterEach
    public void tearDown(){

    }


    @Test
    public void testQuickRideBookingOnePassenger(){
        assertThat(userRepository.findAll().size()).isGreaterThan(0) ;


    }

}
