package com.bedef.redissub.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements StreamListener<String, MapRecord<String, String, String>> {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MessageHandler messageHandler;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        System.out.println("Stream: " + message.getStream() + ";" + "Message: " + message.getValue().get("info"));
        messageHandler.insertInfo(message);
        redisTemplate.opsForStream().acknowledge("bedef-group", message);
    }
}
