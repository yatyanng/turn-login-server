package com.example.turn_rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.example.turn_rest.Constants;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  protected CustomAuthenticationProvider customAuthProvider;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(customAuthProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().csrf().disable().authorizeRequests()
        .antMatchers(Constants.URL_TURN_LOGIN).hasAuthority(Constants.ROLE_USER).antMatchers("/**")
        .hasAuthority(Constants.ROLE_ADMIN);
  }
}

