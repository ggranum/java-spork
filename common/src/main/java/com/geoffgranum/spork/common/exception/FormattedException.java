/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.common.exception;

import com.geoffgranum.spork.common.log.Level;
import com.geoffgranum.spork.common.log.Log;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Just a wrapper that will format messages strings while creating exceptions, for us lazy folk.
 *
 * @author Geoff M. Granum
 */
public class FormattedException extends RuntimeException implements CustomFaultCode {

  private static final long serialVersionUID = 1L;
  private final AtomicBoolean hasBeenLogged = new AtomicBoolean(false);

  private String customFaultCode;

  /**
   * Required for Verify methods that accept an exception class.
   */
  public FormattedException(String message) {
    super(message);
  }

  public FormattedException(String msgFormat, Object... args) {
    this(null, msgFormat, args);
  }

  public FormattedException(Throwable cause, String msgFormat, Object... args) {
    super(FormatSafe(msgFormat, args), cause);
  }

  private static String FormatSafe(String msgFormat, Object[] args) {
    String msg = msgFormat;
    try {
      msg = String.format(msgFormat, args);
    } catch (Exception e) {
      Log.warn(FormattedException.class, "String format failed!");
      Log.error(FormattedException.class, e);
    }
    return msg;
  }

  public FormattedException(Throwable cause) {
    super(cause);
  }

  @Override
  public String getCustomFaultCode() {
    return customFaultCode;
  }

  @Override
  public FormattedException withCustomCode(String code) {
    this.customFaultCode = code;
    return this;
  }

  public void log() {
    if(!hasBeenLogged.get()) {
      hasBeenLogged.set(true);
      Log.log(getLogLevel(), shouldPrintStack(), getThrowingClassName(), this, getLogMessage());
    }
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

  public String getThrowingClassName() {
    return getStackTrace()[1].getClassName();
  }
}
 
