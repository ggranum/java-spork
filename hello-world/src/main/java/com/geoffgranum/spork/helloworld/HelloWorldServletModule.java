/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.helloworld;

import com.geoffgranum.spork.helloworld.resource.HelloWorldResource;
import com.geoffgranum.spork.helloworld.resource.HelloWorldResourceImpl;
import com.geoffgranum.spork.servlet.GuiceResteasyServletModule;

/**
 * @author ggranum
 */
public class HelloWorldServletModule extends GuiceResteasyServletModule {

  @Override
  public void bindResources() {
    bind(HelloWorldResource.class).to(HelloWorldResourceImpl.class);
  }
}
