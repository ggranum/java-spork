/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */
package com.geoffgranum.spork.restclient;

import com.geoffgranum.spork.common.log.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import org.apache.commons.io.IOUtils;

/**
 * @author Geoff M. Granum
 */
public class LoggingClientResponseFilter implements ClientResponseFilter {

  public static final int MAX_RESPONSE_SIZE = 2 * 1024;
  private final AtomicLong maxResponseLogSize = new AtomicLong(0L);

  /**
   * Gets the max response log size from system properties if it has not yet been set.
   * This method is called on each response, but the value is lazily initialized on the first response for this instance of this class.
   * The value is initialized from the property "com.geoffgranum.spork.restclient.maxResponseLogSize", or set to MAX_RESPONSE_SIZE ( 2kb ) by default.
   */
  public long getMaxResponseLogSize() {
    if(maxResponseLogSize.get() == 0) { // we don't synchronize because it's not important if we get a race condition here. Worst case is missing log message.
      long size = MAX_RESPONSE_SIZE;
      String key = "com.geoffgranum.spork.restclient.maxResponseLogSize";
      try {
        size = Long.parseLong(System.getProperty(key, "" + MAX_RESPONSE_SIZE));
      } catch (NumberFormatException e) {
        Log.warn(getClass(), "Could not parse max response log size property '%s' as a long value. Using default of '%s'", key, MAX_RESPONSE_SIZE);
      }
      maxResponseLogSize.set(size);
    }
    return maxResponseLogSize.get();
  }

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
    if(Log.traceEnabled(getClass())) {
      InputStream stream = responseContext.getEntityStream();
      // Stream can be null if response is empty.
      if(stream != null) {
        byte[] bytes = IOUtils.toByteArray(stream);
        if(bytes.length < getMaxResponseLogSize()) {
          responseContext.setEntityStream(new ByteArrayInputStream(bytes));
          Log.trace(getClass(), new String(bytes, StandardCharsets.UTF_8));
        }
      }
    }
  }
}
 
