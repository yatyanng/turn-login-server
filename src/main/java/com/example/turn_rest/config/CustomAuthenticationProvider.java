package com.example.turn_rest.config;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.example.turn_rest.Constants;

@Component
@SuppressWarnings("serial")
public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable {

  private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

  @Value(value = Constants.CFG_ADMIN_USERNAME)
  protected String adminUsername;

  @Value(value = Constants.CFG_ADMIN_PASSWORD)
  protected String adminPassword;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    String username = auth.getName();
    String password = auth.getCredentials().toString();
    log.info("{}:{}", username, password);
    log.info("{}:{}", adminUsername, adminPassword);
    if (StringUtils.equals(username, adminUsername)
        && StringUtils.equals(password, adminPassword)) {
      return new UsernamePasswordAuthenticationToken(username, password,
          Arrays.asList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
              return Constants.ROLE_ADMIN;
            }
          }));
    }
    throw new BadCredentialsException("External system authentication failed");
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }

}
