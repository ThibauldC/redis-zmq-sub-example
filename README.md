# Zero MQ or Redis subscriber example application
## Run application

You can decide whether to use Redis or ZMQ by passing the `queue` argument.

Create jar:
```
mvn clean install
```

### ZMQ (`--queue=zmq`):
```
java -jar sub-0.0.1-SNAPSHOT.jar --queue=zmq --subscriber.channel=fake-channel --zmq.host=10.10.10.6
```

### Redis (`--queue=redis`):
```
java -jar sub-0.0.1-SNAPSHOT.jar --queue=redis --redis.host=10.10.10.6 --redis.port=6379 --subscriber.channel=fake-channel
```

### Arguments
#### Common

- queue: choose between ZMQ or Redis (default: Redis)
- subscriber.channel: channel on which to post the messages

#### ZMQ
- zmq.host: host on which to listen for messages (default: localhost)

#### Redis:
- redis.host: Host of Redis service (default: localhost)
- redis.port: Port of Redis service (default: 6379)
- redis.consumerGroup: Name of consumer group used to subscribe to channels (default: bedef-group)
- redis.consumerName: Name of the actual consumer in the consumer group (default: bedef-consumer)

Here we only use one consumer in the consumer group because all messages in a channel are divided amongst all consumers in a certain group.
