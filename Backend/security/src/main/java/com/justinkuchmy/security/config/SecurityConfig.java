package com.justinkuchmy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.justinkuchmy.security.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
     @Autowired
    private JwtAuthFilter authFilter;

    private static final String[] WHITE_LIST_URLS = {
        "/api/auth/signIn",
        "/api/auth/signUp",
        "/api/v1/products/**",
        "/api/v1/default-controller",
        "/",
        "/index.html",
        "/resource",
        "/h2-console",
        "/h2-console/**",
        "/api/v1/customers/email/**",

    };

    // private static final String[] AUTHENTICATED_URLS = {
    //     "/api/v1/customers",
    // };

    private static final String[] ADMIN_URLS = {
        "/api/v1/customer/all",
        "/api/v1/customer/search",
        "/api/v1/customer/search?**",
        "/api/v1/order/all",
        "/api/v1/orderitem/all",
        "/api/v1/orderitem/**"
        
    };

    //authentication
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST_URLS).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/customer/id").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/customer").permitAll()
                .requestMatchers(ADMIN_URLS).hasAnyAuthority("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
