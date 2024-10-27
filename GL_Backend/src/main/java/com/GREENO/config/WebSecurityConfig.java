package com.GREENO.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.GREENO.security.auth.RestAuthenticationEntryPoint;
import com.GREENO.security.auth.TokenAuthenticationFilter;
import com.GREENO.service.CustomUserDetailsService;
import com.GREENO.util.TokenUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService,
                             RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                             TokenUtils tokenUtils,
                             PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.tokenUtils = tokenUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/users/register").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/products/all").permitAll()
                .antMatchers("/api/products/categories").permitAll()
                .anyRequest().authenticated().and()
                .cors().and()
                .csrf().disable()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);
    }
}