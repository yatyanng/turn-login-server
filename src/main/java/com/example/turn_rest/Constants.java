package com.example.turn_rest;

public class Constants {

  public static final String CARRIER_INFO_STORAGE = "carrierInfoStorage";
  public static final String ROLE_USER = "ROLE_USER";
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  public static final String PARAM_CARRIER = "carrier";
  public static final String PARAM_USERNAME = "username";
  public static final String PARAM_PASSWORD = "password";
  public static final String PARAM_TTL = "ttl";
  public static final String PARAM_URIS = "uris";
  public static final String STR_COLON = ":";
  public static final String URL_TURN_LOGIN = "/turn-login";
  public static final long NUMBER_MILLIS_IN_ONE_SECOND = 1000L;

  public static final String CFG_AUTH_SECRET = "${turn-api.server.auth-secret}";
  public static final String CFG_ADMIN_USER = "${turn-api.server.admin.user}";
  public static final String CFG_ADMIN_PASSWORD = "${turn-api.server.admin.password}";

  public static final String CFG_REDIS_IP_ADDRESS = "${turn-api.redis.ip-address}";
  public static final String CFG_REDIS_PORT = "${turn-api.redis.port}";
  public static final String CFG_REDIS_PASSWORD = "${turn-api.redis.password}";

  private Constants() {}
}
