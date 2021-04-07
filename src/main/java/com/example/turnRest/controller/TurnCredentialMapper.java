package com.example.turnRest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.turnRest.Constants;
import com.example.turnRest.util.CredentialEncoderUtil;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class TurnCredentialMapper extends CustomMapper<TurnCredentialInput, TurnCredential> {

  private static final Logger log = LoggerFactory.getLogger(TurnCredentialMapper.class);

  @Override
  public void mapAtoB(TurnCredentialInput a, TurnCredential b, MappingContext context) {
    try {
      Long expiredTime =
          (System.currentTimeMillis() / Constants.NUMBER_MILLIS_IN_ONE_SECOND) + a.getTimeToLive();
      b.setUsername(String.join(Constants.STR_COLON, String.valueOf(expiredTime), a.getUsername()));
      b.setPassword(CredentialEncoderUtil.encode(b.getUsername(), a.getAuthSecret()));
      b.setTtl(a.getTimeToLive());
      b.setUris(a.getUris());
    } catch (Exception e) {
      log.error("mapAtoB error", e);
    }
  }

}
