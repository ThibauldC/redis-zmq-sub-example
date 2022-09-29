package com.bedef.sub.application.zmq;

import com.bedef.sub.application.MessageSubscriber;
import com.bedef.sub.application.flowable.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMQ;

@Component
@ConditionalOnProperty(name="queue", havingValue = "zmq")
public class ZMQMessageSubscriber implements MessageSubscriber {

    @Value("${zmq.host:localhost}")
    String host;

    @Autowired
    MessageHandler messageHandler;

    @Override
    public void subscribe(String channel) {
        try(ZContext context = new ZContext()) {
            String filter = channel + " ";
            Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect(String.format("tcp://%s:5561", host));
            subscriber.subscribe(filter.getBytes(ZMQ.CHARSET)); // filtering out message that do not start with the filter

            Socket syncClient = context.createSocket(SocketType.REQ);
            syncClient.connect(String.format("tcp://%s:5562", host));

            syncClient.send(ZMQ.MESSAGE_SEPARATOR, 0);
            syncClient.recv(0);

            System.out.println("Subscribed");

            int numberOfUpdates = 1;

            while(true){
                String info = subscriber.recvStr(0);
                //messageHandler.insertInfo(info.replaceFirst(filter, ""));

                //channel names are still part of the message
                if(info.equals(filter + "END")){
                    break;
                }
                System.out.println(numberOfUpdates + ": " + info);
                numberOfUpdates++;
            }
        }
    }
}
