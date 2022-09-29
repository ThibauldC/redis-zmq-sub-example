package com.bedef.redissub.application;

import com.bedef.redissub.application.config.RedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name="queue", havingValue = "redis", matchIfMissing = true)
public class RedisMessageSubscriber implements MessageSubscriber{

    @Autowired
    RedisConfiguration redisConfiguration;

    @Autowired
    StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer;

    @Autowired
    RedisMessageListener redisMessageListener;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    Consumer consumer;

    @Override
    public void subscribe() {
        this.createConsumerGroup();

        streamMessageListenerContainer.receive(consumer, StreamOffset.create(redisConfiguration.getStream(), ReadOffset.lastConsumed()), redisMessageListener);
        streamMessageListenerContainer.start();
    }

    private void createConsumerGroup(){
        //You need a consumer group to be able to continue reading from the latest offset in case of failure
        try{
            redisTemplate.opsForStream()
                    .createGroup(redisConfiguration.getStream(), ReadOffset.from("0-0"), redisConfiguration.getConsumerGroup());
        }
        catch(InvalidDataAccessApiUsageException e){
            System.out.printf("Consumer group %s already exists%n", redisConfiguration.getConsumerGroup());
        }
    }
}
