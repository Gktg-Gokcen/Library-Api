package com.docuart.library.security;

import com.docuart.library.service.UserServices;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {


    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServices();
    }

    @Bean
    @Primary
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
     // http.csrf().disable().authorizeHttpRequests(authz -> authz
     //                         .requestMatchers("/", "/login", "/home", "/register", "/auth/**").permitAll().anyRequest().authenticated()
     //                 //.requestMatchers("/admin/**","/api/book/**","/api/user/**","/dashboard").hasAnyAuthority("ADMIN")
     //                 // .requestMatchers("/account/**","/api/book/**","/dashboard").hasAnyAuthority("USER")
     //         ).logout(logout -> logout
     //                 .logoutUrl("/logout")
     //                 .logoutSuccessUrl("/")
     //                 .permitAll()
     //         )
     //         .exceptionHandling(exceptions -> exceptions
     //                 .accessDeniedPage("/access-denied")
     //         )
     //         .headers(headers -> headers
     //                 .frameOptions(frameOptions -> frameOptions.sameOrigin())
     //         );

        http.csrf().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .requestMatchers("/", "/auth/login", "/auth/**", "/api/user/register")
                .permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/api/book/getall").hasAuthority("USER")
                .requestMatchers("/api/book/**","/api/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }


}