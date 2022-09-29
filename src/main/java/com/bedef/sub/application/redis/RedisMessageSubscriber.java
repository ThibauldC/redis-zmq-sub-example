package com.bedef.sub.application.redis;

import com.bedef.sub.application.MessageSubscriber;
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
public class RedisMessageSubscriber implements MessageSubscriber {

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
    public void subscribe(String channel) {
        this.createConsumerGroup(channel);

        streamMessageListenerContainer.receive(consumer, StreamOffset.create(channel, ReadOffset.lastConsumed()), redisMessageListener);
        streamMessageListenerContainer.start();
    }

    private void createConsumerGroup(String channel){
        //You need a consumer group to be able to continue reading from the latest offset in case of failure
        try{
            redisTemplate.opsForStream()
                    .createGroup(channel, ReadOffset.from("0-0"), redisConfiguration.getConsumerGroup());
        }
        catch(InvalidDataAccessApiUsageException e){
            System.out.printf("Consumer group %s already exists%n", redisConfiguration.getConsumerGroup());
        }
    }
}
