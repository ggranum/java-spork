/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */
package com.geoffgranum.spork.common.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Geoff M. Granum
 */
public class LoggingInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {

    String methodName = " {error} ";
    String className = null;
    Class<?> loggedClass;
    try {
      methodName = invocation.getMethod().getName();
      loggedClass = invocation.getThis().getClass();
      className = loggedClass.getName();
      int idx = className.lastIndexOf("$$Enh");
      if(idx > 0) { // enhanced by guice...
        className = className.substring(0, idx);
      }
      Log.trace(className, "Entering: %s", methodName);
    } catch (Exception e) {
      Log.error(getClass(), "Error performing interceptor logging.", e);
    }
    Object result = null;
    try {
      result = invocation.proceed();
      try {
        if(className != null) {
          Log.trace(className, "Leaving: %s", methodName);
        }
      } catch (Exception e) {
        Log.error(getClass(), "Error performing interceptor logging.", e);
      }
    } catch (Throwable throwable) {
      if(className != null) {
        Log.trace(className, "Leaving: Exception thrown %s: %s",
                  methodName,
                  throwable.getMessage());
      }
      throw throwable; // rethrow the exception, lest we break semantics.
    }
    return result;
  }
}
 
