package com.Employee_Directory_Project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class LoginConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] staticResources = {
                "/css/**",
                "/images/**",
                "/js/**",
                "/vendor/**",
        };

        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") //admin mới được truy cập
                .antMatchers("/user/**").hasRole("USER") //user mới được truy cập
                .antMatchers("/pm/**").hasRole("PM") //pm mới được truy cập
                .antMatchers("/page-recover-pw").permitAll()
                .antMatchers(staticResources).permitAll()
                .anyRequest().authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
                .and()
                .formLogin()
                .loginPage("/sign-in")
                .defaultSuccessUrl("/employee/index", true)
                .permitAll()
                .and()
                .logout() // Cho phép logout\
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .key("uniqueAndSecret")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
    }
}