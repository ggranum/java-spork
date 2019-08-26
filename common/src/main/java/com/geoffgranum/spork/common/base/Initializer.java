/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */
package com.geoffgranum.spork.common.base;

/**
 * @author Geoff M. Granum
 */
public interface Initializer {

  void init();

  boolean initialized();
}
