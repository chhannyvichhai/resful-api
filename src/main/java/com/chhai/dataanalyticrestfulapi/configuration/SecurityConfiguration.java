package com.chhai.dataanalyticrestfulapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 1. create bean of authentication manage
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception{
        return authConf.getAuthenticationManager();
    }

    // 2. Create bean of provider in order to manager to manage
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // basically we have to create three type of beans

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // write the code in order to configure the security

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/authentication/**","/api/v1/file-service/**","/api/v1/email").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

//    @SuppressWarnings("deprecation")








    // 1. user credentials
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        // create three users
//        UserDetails user1 = User.builder()
//                .username("Chhai")
//                .roles("USER")
//                .password("12345")
//                .build();
//        // user
//        UserDetails user2 = User.builder()
//                .username("Momo")
//                .roles("USER")
//                .password("54321")
//                .build();
//        // admin
//        UserDetails user3 = User.builder()
//                .username("Jim")
//                .roles("ADMIN")
//                .password("123456789")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }




//    @Bean
//    public NoOpPasswordEncoder passwordEncoder(){
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }

    // 1. override method loadByUsername from userDetailService
    // authenticationProvider (DaoAuthenticationProvider

    // 2. determine the authentication provider by our own
    // -
}