package com.ftnisa.isa.configuration;

import com.ftnisa.isa.auth.RestAuthenticationEntryPoint;
import com.ftnisa.isa.auth.TokenAuthenticationFilter;
import com.ftnisa.isa.util.TokenUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@OpenAPIDefinition(info = @Info(title = "ISA UBER API", version = "1.0", description = "ISA uber application API"))
@SecurityScheme(name = "isasec", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint, TokenUtils tokenUtils,
                                 UserDetailsService userDetailsService) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                .userDetailsService(userDetailsService)

                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/route/**").permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
                        "/**/*.css", "/**/*.js")
                .permitAll()
                .anyRequest().authenticated().and()

                .cors().and()
                .csrf().disable()

                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userDetailsService),
                        BasicAuthenticationFilter.class);

        return http.build();
    }
}
