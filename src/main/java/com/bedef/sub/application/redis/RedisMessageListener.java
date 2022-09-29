package com.bedef.sub.application.redis;

import com.bedef.sub.application.flowable.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name="queue", havingValue = "redis", matchIfMissing = true)
public class RedisMessageListener implements StreamListener<String, MapRecord<String, String, String>> {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MessageHandler messageHandler;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        System.out.println("Stream: " + message.getStream() + ";" + "Message: " + message.getValue().get("info"));
        //this.handleMessage(message);
        redisTemplate.opsForStream().acknowledge("bedef-group", message);
    }

    private void handleMessage(MapRecord<String, String, String> message){
        if(message.getValue().containsKey("info")){
            messageHandler.insertInfo(message.getValue().get("info"));
        }
        else{
            throw new RuntimeException("Person info not found in message");
        }
    }
}
