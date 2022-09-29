package com.bedef.redissub.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisSubApplication implements CommandLineRunner {

	@Autowired
	MessageSubscriber messageSubscriber;

	public static void main(String[] args) {
		SpringApplication.run(RedisSubApplication.class, args);
	}

	@Override
	public void run(String... args){
		messageSubscriber.subscribe();
	}
}
