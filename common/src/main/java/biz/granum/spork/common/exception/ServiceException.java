/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2015 Geoff M. Granum
 */

package biz.granum.spork.common.exception;

import biz.granum.spork.common.log.Level;
import biz.granum.spork.common.log.Log;
import org.apache.http.HttpStatus;

/**
 *
 * @author Geoff M. Granum
 */
public class ServiceException extends RuntimeException implements HttpResponseAware {

  private static final long serialVersionUID = 1L;

  private boolean handled = false;
  private boolean hasBeenLogged;

  public ServiceException(String msgFormat, Object... args) {
    this(null, msgFormat, args);
  }

  public ServiceException(Throwable cause, String msgFormat, Object... args) {
    super(String.format(msgFormat, args), cause);
  }

  public void markHandled() {
    this.handled = true;
  }

  public boolean handled() {
    return handled;
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
    if(!hasBeenLogged) {
      hasBeenLogged = true;
      Log.log(getLogLevel(), shouldPrintStack(), getClass(), this, getLogMessage());
    }
  }

  @Override
  public int httpResponseCode() {
    return HttpStatus.SC_INTERNAL_SERVER_ERROR;
  }

  @Override
  public String getHttpResponseMessage() {
    return getMessage();
  }

  public String getThrowingClassName() {
    return getStackTrace()[1].getClassName();
  }
}
 
