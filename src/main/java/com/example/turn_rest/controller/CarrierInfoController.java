package com.example.turn_rest.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.turn_rest.Constants;
import com.example.turn_rest.model.CarrierInfo;
import com.example.turn_rest.model.NotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/carrier")
public class CarrierInfoController {

  private static final Logger log = LoggerFactory.getLogger(CarrierInfoController.class);

  @Autowired
  @Qualifier(Constants.CARRIER_INFO_STORAGE)
  private RedisTemplate<String, CarrierInfo> carrierInfoStorage;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Carrier Information", response = Map.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Information OK"),
      @ApiResponse(code = 404, message = "Carrier not found"),
      @ApiResponse(code = 500, message = "An exception has occurred.")})
  public ResponseEntity<CarrierInfo> getCarrierInfo(@RequestParam("name") String carrierName) {
    try {
      log.debug("Getting info for carrierName: {}", carrierName);
      CarrierInfo carrierInfo = carrierInfoStorage.opsForValue().get(carrierName);
      if (carrierInfo == null) {
        throw new NotFoundException(carrierName);
      }
      return new ResponseEntity<>(carrierInfo, HttpStatus.OK);
    } catch (NotFoundException warning) {
      log.warn("get carrier information warning", warning);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception error) {
      log.error("get carrier information error", error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Delete Carrier Information", response = Map.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete Information OK"),
      @ApiResponse(code = 404, message = "Carrier not found"),
      @ApiResponse(code = 500, message = "An exception has occurred.")})
  public ResponseEntity<CarrierInfo> deleteCarrierInfo(@RequestParam("name") String carrierName) {
    try {
      log.debug("Deleting info for carrierName: {}", carrierName);
      CarrierInfo carrierInfo = carrierInfoStorage.opsForValue().get(carrierName);
      if (carrierInfo == null) {
        throw new NotFoundException(carrierName);
      }
      carrierInfoStorage.expireAt(carrierName, new java.util.Date());
      return new ResponseEntity<>(carrierInfo, HttpStatus.OK);
    } catch (NotFoundException warning) {
      log.warn("Delete carrier information error", warning);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception error) {
      log.error("Delete carrier information error", error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Set Carrier Information", response = Map.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Set Carrier Information OK"),
      @ApiResponse(code = 500, message = "An exception has occurred.")})
  public ResponseEntity<CarrierInfo> setCarrierInfo(@RequestBody CarrierInfo carrierInfo) {
    try {
      log.debug("Upserting carrierInfo: {}", carrierInfo);
      carrierInfoStorage.opsForValue().set(carrierInfo.getName(), carrierInfo);
      return new ResponseEntity<>(carrierInfo, HttpStatus.OK);
    } catch (Exception error) {
      log.error("set carrier information error", error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
