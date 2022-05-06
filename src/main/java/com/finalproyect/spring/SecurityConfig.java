package com.finalproyect.spring;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http .csrf().disable() .authorizeRequests() .anyRequest().permitAll();

    }
}
*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**","/callback/", "/webjars/**", "/error**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
    }
}