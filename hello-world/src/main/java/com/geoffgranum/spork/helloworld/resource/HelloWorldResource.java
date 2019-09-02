/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.helloworld.resource;

import com.geoffgranum.spork.helloworld.domain.ServerPerformanceInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author ggranum
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface HelloWorldResource {

  @GET()
  @Path("/hello/{whatever}")
  @Produces(MediaType.TEXT_PLAIN)
  String hello(@PathParam("whatever") String whatever);

  @GET()
  @Path("/version")
  @Produces(MediaType.TEXT_PLAIN)
  String version();

  @GET()
  @Path("/performance/{anyValue}")
  ServerPerformanceInfo perfCheck(@PathParam("anyValue") String anyValue);
}


