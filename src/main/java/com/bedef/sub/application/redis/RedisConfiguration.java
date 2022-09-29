package com.bedef.sub.application.redis;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
@ConditionalOnProperty(name="queue", havingValue = "redis", matchIfMissing = true)
public class RedisConfiguration {
    private String host = "localhost";
    private int port = 6379;
    private String consumerGroup = "bedef-group";
    private String consumerName = "bedef-consumer";
}
