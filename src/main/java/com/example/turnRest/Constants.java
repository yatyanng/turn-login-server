package com.example.turnRest;

public class Constants {

  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  public static final String PARAM_USERNAME = "username";
  public static final String PARAM_PASSWORD = "password";
  public static final String PARAM_TTL = "ttl";
  public static final String PARAM_URIS = "uris";
  public static final String STR_COLON = ":";
  public static final String URL_TURN_LOGIN = "/turn-login";
  public static final String MESSAGE_DIGEST_MD5 = "MD5";
  public static final String STR_AT = "@";

  public static final String CFG_AUTH_URIS = "${turn-api.server.uris}";
  public static final String CFG_AUTH_TTL = "${turn-api.server.ttl}";
  public static final String CFG_AUTH_SECRET = "${turn-api.server.auth-secret}";
  public static final String CFG_ADMIN_USERNAME = "${turn-api.server.admin.username}";
  public static final String CFG_ADMIN_PASSWORD = "${turn-api.server.admin.password}";

  public static final long NUMBER_MILLIS_IN_ONE_SECOND = 1000L;

  private Constants() {}
}
