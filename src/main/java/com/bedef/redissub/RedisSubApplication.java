package com.bedef.redissub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@SpringBootApplication
public class RedisSubApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSubApplication.class, args);
	}

}
