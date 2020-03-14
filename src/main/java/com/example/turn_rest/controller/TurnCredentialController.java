package com.example.turn_rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.turn_rest.Constants;
import com.example.turn_rest.model.CarrierInfo;
import com.example.turn_rest.model.NotFoundException;
import com.example.turn_rest.repo.CarrierInfoRepository;
import com.example.turn_rest.service.CredentialEncoderUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = Constants.URL_TURN_LOGIN)
public class TurnCredentialController {

  private static final Logger log = LoggerFactory.getLogger(TurnCredentialController.class);

  @Autowired
  private CarrierInfoRepository carrierInfoRepository;

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
      String carrierName = auth.getPrincipal().toString();
      Optional<CarrierInfo> carrierInfoOptional = carrierInfoRepository.findById(carrierName);
      if (!carrierInfoOptional.isPresent()) {
        throw new NotFoundException(auth.getPrincipal().toString());
      }
      CarrierInfo carrierInfo = carrierInfoOptional.get();
      Long expiredTime = (System.currentTimeMillis() / Constants.NUMBER_MILLIS_IN_ONE_SECOND)
          + carrierInfo.getTtl();

      String tempUsername = String.join(Constants.STR_COLON, String.valueOf(expiredTime), username);
      map.put(Constants.PARAM_USERNAME, tempUsername);
      map.put(Constants.PARAM_PASSWORD, CredentialEncoderUtil.encode(tempUsername, authSecret));
      map.put(Constants.PARAM_TTL, carrierInfo.getTtl());
      List<String> uris = new ArrayList<>();
      if (carrierInfo.getUris() != null) {
        uris.addAll(carrierInfo.getUris().stream().map(uri -> uri.toString()).collect(Collectors.toList()));
      }
      map.put(Constants.PARAM_URIS, uris);
      log.debug("[{}] returned map: {}", username, map);
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (NotFoundException warning) {
      log.warn("[{}] get turn credentials warning", username, warning);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception error) {
      log.error("[{}] get turn credentials error", username, error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
