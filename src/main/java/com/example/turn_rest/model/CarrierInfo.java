package com.example.turn_rest.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
@Entity
public class CarrierInfo implements Serializable {

  @Id
  private String name;

  @Min(1)
  @NotNull
  private Integer ttl;

  @NotNull
  @Size(max = 100)
  private String password;

  @OneToMany(targetEntity = TurnUri.class, mappedBy = "carrier", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TurnUri> uris;

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

  public List<TurnUri> getUris() {
    return uris;
  }

  public void setUris(List<TurnUri> uris) {
    this.uris = uris;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
