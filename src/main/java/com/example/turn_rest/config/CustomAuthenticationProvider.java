package com.example.turn_rest.config;

import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.example.turn_rest.Constants;
import com.example.turn_rest.model.CarrierInfo;
import com.example.turn_rest.repo.CarrierInfoRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

  @Autowired
  private CarrierInfoRepository carrierInfoRepository;

  @Value(value = Constants.CFG_ADMIN_USER)
  protected String adminUsername;

  @Value(value = Constants.CFG_ADMIN_PASSWORD)
  protected String adminPassword;

  @SuppressWarnings("serial")
  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    String carrierName = auth.getName();
    String password = auth.getCredentials().toString();
    Optional<CarrierInfo> carrierInfoOptional = carrierInfoRepository.findById(carrierName);
    log.info("login: {}, password: {}, carrierInfo found: {}", carrierName, password,
        carrierInfoOptional.isPresent());
    if (StringUtils.equals(carrierName, adminUsername)
        && StringUtils.equals(password, adminPassword)) {
      return new UsernamePasswordAuthenticationToken(carrierName, password,
          Arrays.asList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
              return Constants.ROLE_ADMIN;
            }
          }));
    } else if (carrierInfoOptional.isPresent()) {
      CarrierInfo carrierInfo = carrierInfoOptional.get();
      if (StringUtils.equals(carrierInfo.getPassword(), password)) {
        return new UsernamePasswordAuthenticationToken(carrierName, password,
            Arrays.asList(new GrantedAuthority() {
              @Override
              public String getAuthority() {
                return Constants.ROLE_USER;
              }
            }));
      }
    }
    throw new BadCredentialsException("External system authentication failed");
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }

}
