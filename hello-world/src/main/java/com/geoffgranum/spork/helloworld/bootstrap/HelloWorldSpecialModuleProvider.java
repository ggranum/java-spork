/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.helloworld.bootstrap;

import com.geoffgranum.spork.servlet.bootstrap.BootstrapConfiguration;
import com.geoffgranum.spork.servlet.bootstrap.Env;
import com.geoffgranum.spork.servlet.bootstrap.ModuleProvider;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import java.util.Set;

/**
 * @author ggranum
 */
public class HelloWorldSpecialModuleProvider implements ModuleProvider {

  @Override
  public Set<Module> get(Env env, BootstrapConfiguration baseConfiguration) {
    return ImmutableSet.of(new HelloWorldProductionModule(env));
  }
}
