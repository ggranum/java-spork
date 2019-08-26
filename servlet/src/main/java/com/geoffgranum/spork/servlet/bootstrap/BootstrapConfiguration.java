/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.servlet.bootstrap;

import com.geoffgranum.spork.servlet.util.StaticFromJson;

/**
 * @author ggranum
 */
public interface BootstrapConfiguration extends StaticFromJson {

  int httpPort();

  int httpsPort();

  String jettyHome();

}
