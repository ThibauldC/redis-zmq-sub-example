package com.bedef.redissub.application.flowable;

import org.springframework.data.redis.connection.stream.MapRecord;

public interface MessageHandler {
    void insertInfo(String message);
}
