package com.kirillpolyakov.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirillpolyakov.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/answer/**").hasRole("SIMPLEUSER")
                .antMatchers(HttpMethod.GET, "/answer/**").hasRole("ADMIN")
                .antMatchers("/quiz_result").hasRole("SIMPLEUSER")
                .antMatchers("/request/**").hasRole("ADMIN")
                .antMatchers("/user/authentication").authenticated()
                .antMatchers("/simpleUser/getAll").hasRole("ADMIN")
                .and()
                .httpBasic()
                .authenticationEntryPoint((request, response, e) -> {
                    request.setCharacterEncoding("utf-8");
                    response.setContentType("application/json;charset=utf-8");
                    response.setCharacterEncoding("utf-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    new ObjectMapper().writeValue(response.getWriter(),
                            new ResponseResult<>("No authentication", null));
                });
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
