/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */
package com.geoffgranum.spork.servlet.exception;

import com.geoffgranum.spork.common.exception.FormattedException;
import com.geoffgranum.spork.common.log.Level;
import com.geoffgranum.spork.common.log.Log;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;

/**
 * @author Geoff M. Granum
 */
public class ServiceException extends FormattedException implements HttpResponseAware {

  private static final long serialVersionUID = 1L;

  private static final int STATUS_CODE_UNSET = Integer.MIN_VALUE;

  private final AtomicInteger statusCode = new AtomicInteger(STATUS_CODE_UNSET);

  private final AtomicBoolean hasBeenLogged = new AtomicBoolean(false);

  public ServiceException(String msgFormat, Object... args) {
    super(null, msgFormat, args);
  }

  public ServiceException(Throwable cause, String msgFormat, Object... args) {
    super(String.format(msgFormat, args), cause);
  }

  public ServiceException(int statusCode, String msgFormat, Object... args) {
    super(String.format(msgFormat, args));
    this.statusCode.set(statusCode);
  }

  public ServiceException(Throwable cause, int statusCode, String msgFormat, Object... args) {
    super(cause, msgFormat, args);
    this.statusCode.set(statusCode);
  }


  public Level getLogLevel() {
    return Level.ERROR;
  }

  public boolean shouldPrintStack() {
    return true;
  }

  public String getLogMessage() {
    return getMessage();
  }

  public void log() {
    if(!hasBeenLogged.get()) {
      hasBeenLogged.set(true);
      Log.log(getLogLevel(), shouldPrintStack(), getClass(), this, getLogMessage());
    }
  }

  @Override
  public int statusCode() {
    return this.statusCode.get();
  }

  @Override
  public String getHttpResponseMessage() {
    return getMessage();
  }

  public WebApplicationException asJaxRsException() {
    return new InternalServerErrorException(getMessage(), this);
  }

  public String getThrowingClassName() {
    return getStackTrace()[1].getClassName();
  }

  public boolean hasStatusCode() {
    return this.statusCode.get() != STATUS_CODE_UNSET;
  }
}
 
