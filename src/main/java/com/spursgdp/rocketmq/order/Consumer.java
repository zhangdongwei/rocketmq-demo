package com.spursgdp.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author zhangdongwei
 * @create 2020-02-27-10:32
 * 顺序消费者
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        //1.创建Consumer，指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group3");
        //2.指定NameServer地址
        consumer.setNamesrvAddr("192.168.31.142:9876");
        //3.订阅Topic、Tag
        consumer.subscribe("OrderTopic","OrderTag");
        //4.注册消息监听器，注意这里用的是MessageListenerOrderly
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgList, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg : msgList) {
                    String body = new String(msg.getBody());
                    System.out.println("线程【" + Thread.currentThread().getName() + "】 : " + body);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //5.启动Consumer
        consumer.start();
    }

}
