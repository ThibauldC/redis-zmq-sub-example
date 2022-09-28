package com.bedef.redissub.application;

import com.bedef.redissub.domain.PersonInfo;
import org.springframework.data.redis.connection.stream.MapRecord;

public interface MessageHandler {
    void insertInfo(MapRecord<String, String, String> message);
}
