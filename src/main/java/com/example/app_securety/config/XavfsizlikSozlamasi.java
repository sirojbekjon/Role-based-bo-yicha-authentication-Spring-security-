package com.example.app_securety.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class XavfsizlikSozlamasi extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("director1").password(passwordEncoder().encode("director1")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT","ADD_PRODUCT","EDIT_PRODUCT","DELETE_PRODUCT","READ_ONE_PRODUCT")
                .and()
                .withUser("director2").password(passwordEncoder().encode("director2")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT","ADD_PRODUCT","EDIT_PRODUCT","READ_ONE_PRODUCT")
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager")).roles("MANAGER")
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/api/product/**").hasAnyRole("USER","DIRECTOR","MANAGER")
//                .antMatchers(HttpMethod.GET,"/api/product").hasRole("USER")
//                .antMatchers(HttpMethod.DELETE,"/api/product/*").hasAuthority("DELETE_PRODUCT")
//                .antMatchers("/api/product/**").hasAnyAuthority("READ_ALL_PRODUCT","ADD_PRODUCT","EDIT_PRODUCT","READ_ONE_PRODUCT")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
