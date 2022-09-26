package com.bedef.redissub.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

@Configuration
public class RedisConfiguration {

    @Value("${redis.host:localhost}")
    private String host;
    @Value("${redis.port:6379}")
    private int port;

    @Value("${redis.stream}")
    private String stream;

    @Value("${redis.consumer-group:bedef-group}")
    private String consumerGroup;

    @Value("${redis.consumer-name:bedef-consumer}")
    private String consumerName;


    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);

        return new JedisConnectionFactory(config);
    }

    @Bean
    StringRedisTemplate redisTemplate(){
        final StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());

        return template;
    }

    @Bean
    StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer(){
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .pollTimeout(Duration.ofMillis(100))
                        .build();

        return StreamMessageListenerContainer.create(jedisConnectionFactory(), containerOptions);
    }

    @Bean
    Consumer consumer(){
        return Consumer.from(consumerGroup, consumerName);
    }
}
