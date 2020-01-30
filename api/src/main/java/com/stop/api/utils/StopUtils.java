package com.stop.api.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StopUtils {

  private static final String PREFIX = "-Lw";

  public static String generateCode() {
    return PREFIX + RandomStringUtils.random(17, true, true);
  }

}
