package com.example.turnRest.controller;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class TurnCredential {

  @CsvBindByName(column = "username")
  @CsvBindByPosition(position = 0)
  private String username;

  @CsvBindByName(column = "password")
  @CsvBindByPosition(position = 1)
  private String password;

  @CsvBindByName(column = "ttl")
  @CsvBindByPosition(position = 2)
  private Integer ttl;

  @CsvBindByName(column = "uris")
  @CsvBindByPosition(position = 3)
  private List<String> uris;

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setTtl(Integer ttl) {
    this.ttl = ttl;
  }

  public void setUris(List<String> uris) {
    this.uris = uris;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Integer getTtl() {
    return ttl;
  }

  public List<String> getUris() {
    return uris;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
