package com.bedef.redissub.application;

import com.bedef.redissub.domain.PersonInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Serialized message: " + message.toString());
        System.out.println("Deserialized message: " + mapper.readValue(message.toString(), PersonInfo.class).toString());
    }
}
