/*
 * This software is licensed under the MIT License.
 *
 * Copyright (c) 2019 Geoff M. Granum
 */

package com.geoffgranum.spork.restclient.util;

import com.geoffgranum.spork.restclient.LoggingClientResponseFilter;
import com.geoffgranum.spork.restclient.security.AddAuthHeadersRequestFilter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * Todo: Deprecate this class and make an instance based version, so consumers can subclass it and override specific methods.
 * That said, it's easy enough to copy-paste these utilities into a custom class.
 *
 * @author ggranum
 */
public class ClientHelper {

  public static <T> T createResource(String baseUri, Class<T> resourceClass) {
    return createResource(baseUri, resourceClass, "", "");
  }

  public static <T> T createResource(String baseUri, Class<T> resourceClass, String userName, String password) {
    Client client = createClient(userName, password);
    client.register(new LoggingClientResponseFilter());
    return proxyResource(client, baseUri, resourceClass);
  }

  public static Client createClient(String username, String password) {
    Client client = ClientBuilder.newClient();
    client.register(new AddAuthHeadersRequestFilter(username, password));
    return client;
  }

  public static <T> T proxyResource(Client client, String baseUri, Class<T> resourceToProxy) {
    ResteasyWebTarget target = (ResteasyWebTarget) client.target(baseUri);
    return target.proxy(resourceToProxy);
  }
}
