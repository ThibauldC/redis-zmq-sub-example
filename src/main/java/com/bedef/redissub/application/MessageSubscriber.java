package com.bedef.redissub.application;

public interface MessageSubscriber {
    void subscribe(String channel);
}
