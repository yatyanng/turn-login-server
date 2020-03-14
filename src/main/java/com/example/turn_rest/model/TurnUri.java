package com.example.turn_rest.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.example.turn_rest.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@IdClass(TurnUriId.class)
public class TurnUri implements Serializable {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "carrier_name", referencedColumnName = "name", nullable = false)
  @Id
  @JsonIgnore
  private CarrierInfo carrier;
  
  @NotNull
  @Size(max = 100)
  @Id
  private String scheme;

  @NotNull
  @Size(max = 100)
  @Id
  private String hostname;

  @Min(1)
  @NotNull
  @Id
  private Integer port;

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public CarrierInfo getCarrier() {
    return carrier;
  }

  public void setCarrier(CarrierInfo carrier) {
    this.carrier = carrier;
  }

  public String toString() {
    return String.join(Constants.STR_COLON, scheme, hostname, port.toString());
  }
}
