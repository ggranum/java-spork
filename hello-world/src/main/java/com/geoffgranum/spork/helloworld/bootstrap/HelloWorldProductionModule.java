/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.helloworld.bootstrap;

import com.geoffgranum.spork.common.log.Log;
import com.geoffgranum.spork.servlet.bootstrap.Env;
import com.geoffgranum.spork.servlet.util.GuiceAllowAllCorsFilter;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * @author ggranum
 */
public class HelloWorldProductionModule extends AbstractModule {

  private final Env env;

  public HelloWorldProductionModule(Env env) {
    this.env = env;
  }

  @Override
  protected void configure() {
    Log.info(getClass(), "Configuring module in environment %s", env.key);
    bind(GuiceAllowAllCorsFilter.class).in(Scopes.SINGLETON);
  }
}
