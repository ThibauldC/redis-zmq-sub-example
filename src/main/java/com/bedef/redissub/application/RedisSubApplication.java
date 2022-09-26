package com.bedef.redissub.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import redis.clients.jedis.exceptions.JedisDataException;

@SpringBootApplication
public class RedisSubApplication implements CommandLineRunner {

	@Value("${redis.stream}")
	private String stream;

	@Value("${redis.consumer-group:bedef-group}")
	private String consumerGroup;

	@Autowired
	StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer;

	@Autowired
	RedisMessageListener redisMessageListener;

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	Consumer consumer;

	public static void main(String[] args) {
		SpringApplication.run(RedisSubApplication.class, args);
	}

	@Override
	public void run(String... args){
		streamMessageListenerContainer.receive(consumer, StreamOffset.create(stream, ReadOffset.lastConsumed()), redisMessageListener);
		streamMessageListenerContainer.start();
	}

	private void createConsumerGroup(){
		//You need a consumer group to be able to continue reading from the latest offset
		try{
			redisTemplate.opsForStream().createGroup(stream, ReadOffset.from("0-0"), "bedef-group");
		}
		catch(InvalidDataAccessApiUsageException e){
			System.out.println("Consumer group already exists");
		}
	}
}
