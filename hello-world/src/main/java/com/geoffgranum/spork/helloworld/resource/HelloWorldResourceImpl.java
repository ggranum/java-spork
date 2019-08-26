/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.helloworld.resource;

import com.geoffgranum.spork.common.base.VersionInfo;
import com.geoffgranum.spork.servlet.bootstrap.Env;
import javax.inject.Inject;

/**
 * @author ggranum
 */
public class HelloWorldResourceImpl implements HelloWorldResource {

  private final Env env;
  private final VersionInfo appVersion;

  @Inject
  private HelloWorldResourceImpl(Env env, VersionInfo appVersion) {

    this.env = env;
    this.appVersion = appVersion;
  }

  @Override
  public String hello(String whatever) {
    return "Hello " + whatever;
  }

  @Override
  public String version() {
    return appVersion.toString() + " in environment mode " + env.key;
  }
}
