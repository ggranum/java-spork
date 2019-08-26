/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */
package com.geoffgranum.spork.common.exception.service;

import com.geoffgranum.spork.common.exception.FormattedException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Geoff M. Granum
 */
public class ServiceException extends FormattedException implements HttpResponseAware {

  private static final long serialVersionUID = 1L;

  private static final int STATUS_CODE_UNSET = Integer.MIN_VALUE;

  private final AtomicInteger statusCode = new AtomicInteger(STATUS_CODE_UNSET);

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

  @Override
  public int statusCode() {
    return this.statusCode.get();
  }

  @Override
  public String getHttpResponseMessage() {
    return getMessage();
  }

  public boolean hasStatusCode() {
    return this.statusCode.get() != STATUS_CODE_UNSET;
  }
}
 
