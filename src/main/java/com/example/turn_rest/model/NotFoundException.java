package com.example.turn_rest.model;

@SuppressWarnings("serial")
public class NotFoundException extends Exception {

  private String carrierName;

  public String getCarrierName() {
    return carrierName;
  }

  public NotFoundException(String carrierName) {
    this.carrierName = carrierName;
  }

  @Override
  public String getMessage() {
    return "Carrier not found: " + getCarrierName();
  }
}
