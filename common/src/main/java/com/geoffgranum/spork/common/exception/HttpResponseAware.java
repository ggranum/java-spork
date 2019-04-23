/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2015 Geoff M. Granum
 */

package com.geoffgranum.spork.common.exception;

/**
 * @author Geoff M. Granum
 */
public interface HttpResponseAware {

  int httpResponseCode();

  String getHttpResponseMessage();
}
