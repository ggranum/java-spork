/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */
package com.geoffgranum.spork.restclient;

import com.geoffgranum.spork.common.log.Log;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.net.URLDecoder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class RequestLoggingFilter implements ClientRequestFilter {

  @Override
  public void filter(ClientRequestContext requestContext) throws IOException {
    String uri = URLDecoder.decode(requestContext.getUri().toString(), Charsets.UTF_8.name());
    Log.trace(getClass(), "Making request: %s", uri);
  }
}
 
