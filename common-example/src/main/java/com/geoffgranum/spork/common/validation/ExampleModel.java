/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2015 Geoff M. Granum
 */

package com.geoffgranum.spork.common.validation;

/**
 * @author Geoff M. Granum
 */
public final class ExampleModel {

  public final String name;
  public final int awesomeFactor;
  public final double magicExponent;

  private ExampleModel(Builder builder) {
    name = builder.name;
    awesomeFactor = builder.awesomeFactor;
    magicExponent = builder.magicExponent;
  }

  public static final class Builder {

    private String name;
    private Integer awesomeFactor;
    private Double magicExponent;

    public Builder() {
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder awesomeFactor(int awesomeFactor) {
      this.awesomeFactor = awesomeFactor;
      return this;
    }

    public Builder magicExponent(double magicExponent) {
      this.magicExponent = magicExponent;
      return this;
    }

    public ExampleModel build() {
      return new ExampleModel(this);
    }
  }
}
 
