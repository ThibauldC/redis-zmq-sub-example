package com.bedef.redissub.application.flowable;

import com.bedef.redissub.domain.PersonInfo;
import com.bedef.redissub.feign.PersonInfoClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlowableHandler implements MessageHandler {

    @Autowired
    PersonInfoClient client;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void insertInfo(String message) {
        PersonInfo info = mapper.readValue(message, PersonInfo.class);
        client.insertInfo(info);
    }
}
