package com.stop.api.utils;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ResponseUtils {

  private ResponseUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static URI getCreationUri(HttpServletRequest req, Long id) {
    return ServletUriComponentsBuilder.fromRequest(req).path("/" + id).build().toUri();
  }

}
