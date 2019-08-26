/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.servlet.bootstrap;

import com.geoffgranum.spork.common.base.Initializer;
import com.geoffgranum.spork.common.base.VersionInfo;
import com.geoffgranum.spork.servlet.initialization.InitializationChain;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * @author ggranum
 */
public final class SporkBootstrapModule extends AbstractModule {

  private final Bootstrap bootstrap;
  private final Env env;
  private final VersionInfo applicationVersion;
  private final InitializationChain initializationChain;

  public SporkBootstrapModule(Env env,
                              Bootstrap bootstrap,
                              VersionInfo applicationVersion,
                              InitializationChain initializationChain) {
    this.bootstrap = bootstrap;
    this.env = env;
    this.applicationVersion = applicationVersion;
    this.initializationChain = initializationChain;
  }

  @Override
  protected void configure() {
    bind(Env.class).toInstance(env);
    bind(Bootstrap.class).toInstance(bootstrap);
    bind(VersionInfo.class).toInstance(applicationVersion);
    bind(bootstrap.configurationClass).toInstance(bootstrap.baseConfiguration());
    this.bindInit();
  }

  private void bindInit() {
    bind(Initializer.class).to(ApplicationInitializer.class).in(Scopes.SINGLETON);
    bind(InitializationChain.class).toInstance(this.initializationChain);
  }
}
