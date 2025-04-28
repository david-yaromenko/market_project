package com.example.myShop.configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisConfiguration {

    @Configuration
    public class RedisConfig {

        @Bean
        public RedisClient redisClient(@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") int port) {
            return RedisClient.create("redis://" + host + ":" + port + "/0");
        }

        @Bean
        public StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
            return redisClient.connect();
        }

        @Bean
        public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> connection) {
            return connection.sync();
        }
    }


}
