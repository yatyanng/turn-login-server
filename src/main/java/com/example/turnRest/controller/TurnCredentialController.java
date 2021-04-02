package com.example.turnRest.controller;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
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
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@RestController
@RequestMapping(value = Constants.URL_TURN_LOGIN)
public class TurnCredentialController {

  private static final Logger log = LoggerFactory.getLogger(TurnCredentialController.class);

  @Value(value = Constants.CFG_AUTH_TTL)
  protected Integer ttl;

  @Value(value = Constants.CFG_AUTH_URIS)
  protected String[] uris;

  @Value(value = Constants.CFG_AUTH_SECRET)
  protected String authSecret;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get TURN Credentials", response = Map.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Get TURN credentials OK"), @ApiResponse(
          code = 500, message = "An exception has occurred while getting a TURN credentials.")})
  public ResponseEntity<TurnCredential> getCredentials(@RequestParam("username") String username) {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      log.info("[{}] auth: {}", username, auth.getPrincipal());
      TurnCredentialInput input =
          new TurnCredentialInput(username, authSecret, ttl, Arrays.asList(uris));
      log.info("[{}] credential input: {}", username, input);
     
      MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
      mapperFactory.classMap(TurnCredentialInput.class, TurnCredential.class)
          .customize(new TurnCredentialMapper()).register();
      BoundMapperFacade<TurnCredentialInput, TurnCredential> boundMapper =
          mapperFactory.getMapperFacade(TurnCredentialInput.class, TurnCredential.class);
      TurnCredential output = boundMapper.map(input);
      log.info("[{}] credential ouput: {}", username, output);
      
      Writer writer = new StringWriter();
      StatefulBeanToCsv<TurnCredential> beanToPipes =
          new StatefulBeanToCsvBuilder<TurnCredential>(writer).withSeparator('|')
              .withQuotechar('\0').build();
      beanToPipes.write(output);

      log.info("[{}] output in pipe-sv format: {}", username, writer.toString());
      writer.close();
      return new ResponseEntity<>(output, HttpStatus.OK);
    } catch (Exception error) {
      log.error("[{}] get turn credentials error", username, error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
