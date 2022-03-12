package com.example.sns.infrastructure.webflux;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
public class CustomWebFluxConfigurer implements WebFluxConfigurer {

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
    configurer.addCustomResolver(new ClientIpArgumentResolver());
    WebFluxConfigurer.super.configureArgumentResolvers(configurer);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*");

    WebFluxConfigurer.super.addCorsMappings(registry);
  }
}
