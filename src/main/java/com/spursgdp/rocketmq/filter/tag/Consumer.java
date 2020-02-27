package com.spursgdp.rocketmq.filter.tag;

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
 * 消费者（Tag过滤）
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        //1.创建Consumer，指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定NameServer地址
        consumer.setNamesrvAddr("192.168.31.142:9876");

        //3.订阅Topic、Tag
        consumer.subscribe("FilterTopic","Tag1");  //接收Tag1
//        consumer.subscribe("FilterTopic","Tag1 || Tag2");   //接收Tag1、Tag2
//        consumer.subscribe("FilterTopic","*");  //不过滤，该主题下的所有Tag都接收

        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接收消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                for (MessageExt msg : msgList) {
                    System.out.println("接收消息：" + msg.getTags() + " " + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动Consumer
        consumer.start();
        System.out.println("消费者启动...");
    }

}
