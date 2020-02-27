package com.spursgdp.rocketmq.filter.tag;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author zhangdongwei
 * @create 2020-02-27-8:30
 * 发送同步消息
 */
public class Producer2 {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        //1.创建producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        //2.指定NameServer地址
        producer.setNamesrvAddr("192.168.31.142:9876");

        //3.启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //4.创建消息对象Message，指定Topic、Tag、消息体
            String content = "Hello World " + i;
            Message message = new Message("FilterTopic", "Tag2", content.getBytes());
            //5.发送消息（默认为同步）
            SendResult sendResult = producer.send(message);
            //打印发送结果
            System.out.println("发送结果:" + sendResult);

            Thread.sleep(1000);
        }

        //6.关闭producer
        producer.shutdown();

    }
}
