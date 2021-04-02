package com.example.turnRest.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.turnRest.Constants;
import com.example.turnRest.util.CredentialEncoderUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = Constants.URL_TURN_LOGIN)
public class TurnCredentialController {

  private static final Logger log = LoggerFactory.getLogger(TurnCredentialController.class);

  @Value(value = Constants.CFG_AUTH_TTL)
  protected int ttl;

  @Value(value = Constants.CFG_AUTH_URIS)
  protected String[] uris;
  
  @Value(value = Constants.CFG_AUTH_SECRET)
  protected String authSecret;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get TURN Credentials", response = Map.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Get TURN credentials OK"),
      @ApiResponse(code = 404, message = "Carrier not found"), @ApiResponse(code = 500,
          message = "An exception has occurred while getting a TURN credentials.")})
  public ResponseEntity<Map<String, Object>> getCredentials(
      @RequestParam("username") String username) {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      log.debug("[{}] auth: {}", username, auth.getPrincipal());
      Map<String, Object> map = new HashMap<>();

      Long expiredTime = (System.currentTimeMillis() / Constants.NUMBER_MILLIS_IN_ONE_SECOND) + ttl;

      String tempUsername = String.join(Constants.STR_COLON, String.valueOf(expiredTime), username);
      map.put(Constants.PARAM_USERNAME, tempUsername);
      map.put(Constants.PARAM_PASSWORD, CredentialEncoderUtil.encode(tempUsername, authSecret));
      map.put(Constants.PARAM_TTL, ttl);
      map.put(Constants.PARAM_URIS, Arrays.asList(uris));
      log.debug("[{}] returned map: {}", username, map);
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception error) {
      log.error("[{}] get turn credentials error", username, error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
