package com.bedef.redissub.application.flowable;

import com.bedef.redissub.domain.PersonInfo;
import com.bedef.redissub.feign.PersonInfoClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonEncoder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.stereotype.Component;

@Component
public class FlowableHandler implements MessageHandler{

    @Value("${flowable.url:http://localhost:8080}")
    private String flowableURL;

    //All status codes other than 2XX will trigger an error
    // custom error decoder possible: https://github.com/OpenFeign/feign#error-handling
    private final PersonInfoClient client = Feign.builder()
            .encoder(new JacksonEncoder())
            .target(PersonInfoClient.class, "http://localhost:8080");

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void insertInfo(MapRecord<String, String, String> message) {
        if(message.getValue().containsKey("info")){
            PersonInfo info = this.mapper.readValue(message.getValue().get("info"), PersonInfo.class);
            this.client.insertInfo(info);
        }
        else{
            throw new RuntimeException("Person info not found in message");
        }
    }
}
