package com.example.turn_rest.config;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.example.turn_rest.Constants;
import com.example.turn_rest.model.CarrierInfo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

  @Autowired
  @Qualifier(Constants.CARRIER_INFO_STORAGE)
  private RedisTemplate<String, CarrierInfo> carrierInfoStorage;

  @Value(value = Constants.CFG_ADMIN_USER)
  protected String adminUsername;

  @Value(value = Constants.CFG_ADMIN_PASSWORD)
  protected String adminPassword;

  @SuppressWarnings("serial")
  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    String username = auth.getName();
    String password = auth.getCredentials().toString();
    CarrierInfo carrierInfo = carrierInfoStorage.opsForValue().get(username);
    log.info("login: {}, password: {}, carrierInfo: {}", username, password, carrierInfo);
    if (StringUtils.equals(username, adminUsername)
        && StringUtils.equals(password, adminPassword)) {
      return new UsernamePasswordAuthenticationToken(username, password,
          Arrays.asList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
              return Constants.ROLE_ADMIN;
            }
          }));
    } else if (carrierInfo != null && StringUtils.equals(password, carrierInfo.getPassword())) {
      return new UsernamePasswordAuthenticationToken(username, password,
          Arrays.asList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
              return Constants.ROLE_USER;
            }
          }));
    } else {
      throw new BadCredentialsException("External system authentication failed");
    }
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }

}
