package com.example.turn_rest.controller;

import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.turn_rest.model.CarrierInfo;
import com.example.turn_rest.model.NotFoundException;
import com.example.turn_rest.repo.CarrierInfoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/carrier")
public class CarrierInfoController {

  private static final Logger log = LoggerFactory.getLogger(CarrierInfoController.class);

  @Autowired
  private CarrierInfoRepository carrierInfoRepository;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Carrier Information", response = Map.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Carrier Information OK"),
      @ApiResponse(code = 404, message = "Carrier not found"),
      @ApiResponse(code = 500, message = "An exception has occurred.")})
  public ResponseEntity<CarrierInfo> getCarrierInfo(@RequestParam("name") String carrierName) {
    try {
      log.debug("Getting info for carrierName: {}", carrierName);
      Optional<CarrierInfo> carrierInfoOptional = carrierInfoRepository.findById(carrierName);
      if (!carrierInfoOptional.isPresent()) {
        throw new NotFoundException(carrierName);
      }
      return new ResponseEntity<>(carrierInfoOptional.get(), HttpStatus.OK);
    } catch (NotFoundException warning) {
      log.warn("Get carrier information warning", warning);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception error) {
      log.error("Get carrier information error", error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Delete Carrier Information", response = Map.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete Carrier Information OK"),
      @ApiResponse(code = 404, message = "Carrier not found"),
      @ApiResponse(code = 500, message = "An exception has occurred.")})
  public ResponseEntity<CarrierInfo> deleteCarrierInfo(@RequestParam("name") String carrierName) {
    try {
      log.debug("Deleting info for carrierName: {}", carrierName);
      Optional<CarrierInfo> carrierInfoOptional = carrierInfoRepository.findById(carrierName);
      if (!carrierInfoOptional.isPresent()) {
        throw new NotFoundException(carrierName);
      }
      carrierInfoRepository.delete(carrierInfoOptional.get());
      return new ResponseEntity<>(carrierInfoOptional.get(), HttpStatus.OK);
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
      if (carrierInfo.getUris() != null) {
        carrierInfo.getUris().stream().forEach(uri -> uri.setCarrier(carrierInfo));
      }
      carrierInfoRepository.save(carrierInfo);
      return new ResponseEntity<>(carrierInfo, HttpStatus.OK);
    } catch (Exception error) {
      log.error("Set carrier information error", error);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
