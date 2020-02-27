package com.spursgdp.rocketmq.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangdongwei
 * @create 2020-02-27-8:30
 * 批量发送消息
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        //1.创建producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        //2.指定NameServer地址
        producer.setNamesrvAddr("192.168.31.142:9876");

        //3.启动producer
        producer.start();

        //生成批量消息
        Message msg1 = new Message("BatchTopic", "Tag1", "Hello World 1".getBytes());
        Message msg2 = new Message("BatchTopic", "Tag1", "Hello World 2".getBytes());
        Message msg3 = new Message("BatchTopic", "Tag1", "Hello World 3".getBytes());
        List<Message> messages = Arrays.asList(msg1, msg2, msg3);

        //4. 发送消息
        SendResult sendResult = producer.send(messages);

        //打印发送结果
        System.out.println("发送结果:" + sendResult);

        //6.关闭producer
        producer.shutdown();

    }
}
