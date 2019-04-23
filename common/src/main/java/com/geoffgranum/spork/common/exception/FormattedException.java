/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.common.exception;

import com.geoffgranum.spork.common.log.Log;

/**
 * Just a wrapper that will format messages strings while creating exceptions, for us lazy folk.
 *
 * @author Geoff M. Granum
 */
public abstract class FormattedException extends RuntimeException implements CustomFaultCode
{

  private static final long serialVersionUID = 1L;

  private String customFaultCode;

  public FormattedException(String msgFormat, Object... args)
  {
    this(null, msgFormat, args);
  }

  public FormattedException(Throwable cause, String msgFormat, Object... args)
  {
    super(FormatSafe(msgFormat, args), cause);
  }

  public FormattedException(Throwable cause)
  {
    super(cause);
  }

  @Override
  public String getCustomFaultCode()
  {
    return customFaultCode;
  }

  @Override
  public FormattedException withCustomCode(String code)
  {
    this.customFaultCode = code;
    return this;
  }

  private static String FormatSafe(String msgFormat, Object[] args)
  {
    String msg = msgFormat;
    try
    {
      msg = String.format(msgFormat, args);
    } catch (Exception e)
    {
      Log.warn(FormattedException.class, "String format failed!");
      Log.error(FormattedException.class, e);
    }
    return msg;
  }
}
 
