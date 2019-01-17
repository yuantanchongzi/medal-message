package com.message.product.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Francis
 * @Description:
 * @TIME: Created on 2018/11/4
 * @Modified by:
 */

@Component
public class SendMQ {

    public void sendRocketMQ(int id){
        DefaultMQProducer producer = new DefaultMQProducer("DefaultCluster");
        producer.setNamesrvAddr("localhost:9876");
//        producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
        producer.setVipChannelEnabled(false);
        producer.setInstanceName("producer");

        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        Map medalStatus = new HashMap();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        medalStatus.put("time",dateFormat.format(new Date()));
        medalStatus.put("status",id);

        try {
            for (int i = 0; i < 1; i++) {
                Thread.sleep(5000); //每5秒发送一次MQ
                Message msg = new Message("TopicA-test", "TagA",medalStatus.toString().getBytes());
                SendResult sendResult = producer.send(msg);
                System.out.println(medalStatus.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
//
//    public static void main(String[] arg) {
//        SendMQ mq = new SendMQ();
//        mq.sendRocketMQ();
//    }
}
