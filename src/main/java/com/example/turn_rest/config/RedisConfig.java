package com.example.turn_rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import com.example.turn_rest.Constants;
import com.example.turn_rest.model.CarrierInfo;

@Configuration
public class RedisConfig {

  @Value(value = Constants.CFG_REDIS_IP_ADDRESS)
  protected String ipAddress;

  @Value(value = Constants.CFG_REDIS_PORT)
  protected int port;

  @Value(value = Constants.CFG_REDIS_PASSWORD)
  protected String password;

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(ipAddress, port);
    config.setPassword(password);
    return new JedisConnectionFactory(config);
  }

  @Bean(Constants.CARRIER_INFO_STORAGE)
  public RedisTemplate<String, CarrierInfo> carrierInfoStorage(
      JedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, CarrierInfo> carrierInfoStorage = new RedisTemplate<>();
    carrierInfoStorage.setConnectionFactory(redisConnectionFactory);
    return carrierInfoStorage;
  }
}
