package com.message.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MessageConsumerApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MessageConsumerApplication.class, args);
	}


    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("DefaultCluster");

        consumer.setNamesrvAddr("localhost:9876");
        consumer.setInstanceName("consumer");
        try {
            consumer.subscribe("TopicA-test", "TagA");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(new String(msg.getTopic()));
                    System.out.println(new String(msg.getTags()));
                    System.out.println("=== " + new String(msg.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
