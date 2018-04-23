package com.ing.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/products/*").hasAnyRole("AGENT", "OPERATIONS_MANAGER")
                .antMatchers("/products/*").hasRole("OPERATIONS_MANAGER")
                .antMatchers("/products").hasRole("OPERATIONS_MANAGER")
                .and().httpBasic()
                .and()
                .csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("test")
                .password("test123").roles("AGENT").and()
                .withUser("ingtest")
                .password("test123test123").roles("OPERATIONS_MANAGER");
    }
    
    @SuppressWarnings("deprecation")
	@Bean
    public static NoOpPasswordEncoder passwordEncoder() {
     return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}
