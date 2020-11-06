package com.steve.springbootstarter.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProxyConfig {
  
  @Value("${users.api.url.v1}")
  private String USERS_URL;

  @Bean
  public UserResourceV1 getUserResourceV1() {
    ResteasyClient client = new ResteasyClientBuilderImpl().build();

    ResteasyWebTarget target = client.target(USERS_URL);

    return target.proxy(UserResourceV1.class);
  }
}
