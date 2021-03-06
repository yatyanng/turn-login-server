package com.example.turnRest.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CredentialEncoderUtil {

  private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

  private static String toBase64String(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static String encode(String data, String key)
      throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
    SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
    Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
    mac.init(signingKey);
    return toBase64String(mac.doFinal(data.getBytes()));
  }
}
