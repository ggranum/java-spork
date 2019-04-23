/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2015 Geoff M. Granum
 */

package com.geoffgranum.spork.common.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @author Geoff M. Granum
 */
@JsonDeserialize(builder = ExampleModel.Builder.class)
public class ExampleModel {

  public final String name;
  public final int awesomeFactor;
  public final double magicExponent;

  private ExampleModel(Builder builder) {
    name = builder.name;
    awesomeFactor = builder.awesomeFactor;
    magicExponent = builder.magicExponent;
  }

  public static final class Builder extends Validated {

    @NotNull @Length(min = 1, max = 100) @JsonProperty private String name;
    @NotNull @Range(min = 0, max = 11) @JsonProperty private Integer awesomeFactor;

    @NotNull
    @Range(min = 1, max = 10, message = "Really? '${validatedValue}'? That's some crazy magic exponent buddy. How bout making it between {min} and {max}?")
    @JsonProperty
    private Double magicExponent;

    /*
      ExampleModel exampleModel = new ExampleModel.Builder()
      .name( input.getName() )
      .awesomeFactor( input.getAwesomeFactor() )
      .magicExponent( input.getMagicExponent() )
      .build();
    */
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

    public Builder from(ExampleModel copy) {
      name = copy.name;
      awesomeFactor = copy.awesomeFactor;
      magicExponent = copy.magicExponent;
      return this;
    }

    public ExampleModel build() {
      checkValid();
      return new ExampleModel(this);
    }
  }
}
 
