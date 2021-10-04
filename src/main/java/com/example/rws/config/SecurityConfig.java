package com.example.rws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //h2 DB console 사용을 위해
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2-console/**"));
        http.headers().frameOptions().disable(); //X-Frame-Options in Spring Security 중지
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("test")
                .password("{noop}test123")
                .roles("USER");
    }
}
