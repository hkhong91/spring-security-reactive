package com.example.demo.infrastructure.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

  @Bean
  public ReactiveRedisTemplate<String, Integer> numberTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
    StringRedisSerializer keySerializer = new StringRedisSerializer();
    GenericToStringSerializer<Integer> valueSerializer = new GenericToStringSerializer<>(Integer.class);
    RedisSerializationContext.RedisSerializationContextBuilder<String, Integer> contextBuilder =
        RedisSerializationContext.newSerializationContext(keySerializer);
    RedisSerializationContext<String, Integer> context = contextBuilder.value(valueSerializer).build();
    return new ReactiveRedisTemplate<>(lettuceConnectionFactory, context);
  }

  @Bean
  public ReactiveValueOperations<String, Integer> numberOperations(ReactiveRedisTemplate<String, Integer> numberTemplate) {
    return numberTemplate.opsForValue();
  }
}
