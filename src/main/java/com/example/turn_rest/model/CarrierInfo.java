package com.example.turn_rest.model;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class CarrierInfo implements Serializable {

  private String name;
  private Integer ttl;
  private String password;
  private List<String> uris;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getTtl() {
    return ttl;
  }

  public void setTtl(Integer ttl) {
    this.ttl = ttl;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getUris() {
    return uris;
  }

  public void setUris(List<String> uris) {
    this.uris = uris;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
