package com.finalproyect.spring;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        /*http.authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/metrics").hasAnyRole("USER")
                .and()
                .authorizeRequests()
                .antMatchers("/loggers").hasRole("USER");*/
    }
    }
