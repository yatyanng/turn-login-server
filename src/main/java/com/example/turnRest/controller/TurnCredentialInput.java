package com.example.turnRest.controller;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TurnCredentialInput {

  private String username;
  private String authSecret;
  private Integer timeToLive;
  private List<String> uris;

  public TurnCredentialInput(String username, String authSecret, Integer timeToLive,
      List<String> uris) {
    this.username = username;
    this.authSecret = authSecret;
    this.timeToLive = timeToLive;
    this.uris = uris;
  }

  public String getUsername() {
    return username;
  }

  public String getAuthSecret() {
    return authSecret;
  }

  public Integer getTimeToLive() {
    return timeToLive;
  }

  public List<String> getUris() {
    return uris;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
