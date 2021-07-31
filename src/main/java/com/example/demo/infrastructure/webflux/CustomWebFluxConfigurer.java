package com.example.demo.infrastructure.webflux;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
public class CustomWebFluxConfigurer implements WebFluxConfigurer {

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
    configurer.addCustomResolver(new ClientIpArgumentResolver());
    WebFluxConfigurer.super.configureArgumentResolvers(configurer);
  }
}
