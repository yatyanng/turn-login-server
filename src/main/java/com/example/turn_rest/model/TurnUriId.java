package com.example.turn_rest.model;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class TurnUriId implements Serializable {

  private String scheme;
  private String hostname;
  private Integer port;
  private CarrierInfo carrier;

  public TurnUriId() {}

  public TurnUriId(String scheme, String hostname, Integer port, CarrierInfo carrier) {
    this.scheme = scheme;
    this.hostname = hostname;
    this.port = port;
    this.carrier = carrier;
  }

  public String getScheme() {
    return scheme;
  }

  public String getHostname() {
    return hostname;
  }

  public Integer getPort() {
    return port;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
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

  public boolean equals(TurnUriId uriId) {
    return Objects.equals(uriId.getScheme(), scheme) && uriId.getCarrier() != null
        && Objects.equals(uriId.getHostname(), hostname) && carrier != null
        && Objects.equals(uriId.getCarrier().getName(), carrier.getName())
        && Objects.equals(uriId.getPort(), port);
  }
}
