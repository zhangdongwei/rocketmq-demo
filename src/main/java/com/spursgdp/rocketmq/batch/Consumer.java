package com.spursgdp.rocketmq.batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author zhangdongwei
 * @create 2020-02-27-10:32
 * 消费者
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        //1.创建Consumer，指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定NameServer地址
        consumer.setNamesrvAddr("192.168.31.142:9876");
        //3.订阅Topic、Tag
        consumer.subscribe("BatchTopic","Tag1");
        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接收消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                for (MessageExt msg : msgList) {
                    byte[] body = msg.getBody();
                    System.out.println("接收消息："+new String(body));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动Consumer
        consumer.start();
        System.out.println("消费者启动...");
    }

}
