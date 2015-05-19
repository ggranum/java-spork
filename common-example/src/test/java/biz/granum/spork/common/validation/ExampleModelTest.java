/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2015 Geoff M. Granum
 */

package biz.granum.spork.common.validation;

import biz.granum.spork.common.log.Log;
import java.io.File;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExampleModelTest {

  static {
    /* Obviously you should set this somewhere else. Like in your build configuration.  */
    File f = new File("./log4j2-dev.xml");
    if(!f.exists()) {
      String msg = String.format("The log configuration file is located in the project root directory, but "
                                 + "the system is attempting to load it from '%s'. Check that your base run directory "
                                 + "is set to your project root directory. It is currently %s. %s",
                                 f.getAbsolutePath(),
                                 f.getParentFile().getAbsolutePath(),
                                 System.getProperty("workingDirectory")
      );
      System.err.println(msg); // gradle won't really print it otherwise. You'd have to open the test report.
      throw new RuntimeException(msg);
    }
    System.setProperty("log4j.configurationFile", "log4j2-dev.xml");
  }
  @Test
  public void testCanBuildValidExampleModel() throws Exception {
    ExampleModel model = new ExampleModel.Builder()
                             .awesomeFactor(10)
                             .name("Narwhal")
                             .magicExponent(1.0001)
                             .build();
    assertThat(model, notNullValue());
    assertThat(model.awesomeFactor, is(10));
    assertThat(model.name, is("Narwhal"));
    assertThat(model.magicExponent, Matchers.closeTo(1.0001, .00001));
    Log.warn(ExampleModelTest.class, "Narwhals versus %s: who would win?", Math.random() < 0.5 ? "Killer whale" : "Honey Badger");
  }

  @Test
  public void testAwesomeFactorCannotGoTo12() throws Exception {
    ExampleModel.Builder builder = new ExampleModel.Builder()
                                       .awesomeFactor(11)
                                       .name("Narwhal")
                                       .magicExponent(1.0001);
    ExampleModel model = builder.build();
    assertThat(model, notNullValue());

    ValidationException ve = null;
    try {
      model = builder.awesomeFactor(12).build();
    } catch (ValidationException e) {
      ve = e;
    }

    assertThat(ve, notNullValue());
    assertThat(model.awesomeFactor, is(11));
  }
}