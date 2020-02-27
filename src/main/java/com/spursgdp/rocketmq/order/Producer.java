package com.spursgdp.rocketmq.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @author zhangdongwei
 * @create 2020-02-27-8:30
 * 发送顺序消息（根据orderId选择queueId）
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        //1.创建producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        //2.指定NameServer地址
        producer.setNamesrvAddr("192.168.31.142:9876");

        //3.启动producer
        producer.start();

        //4.发送数据
        List<OrderMessage> orders = OrderMessage.buildOrders();
        for (int i = 0; i < orders.size(); i++) {
            OrderMessage order = orders.get(i);
            Message msg = new Message("OrderTopic", "OrderTag", order.toString().getBytes());
            /**
             * 参数1：消息对象
             * 参数2：消息队列选择器（自定义函数）
             * 参数3：选择队列的业务标识（这里用orderId）
             */
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                /**
                 * @param queues 队列集合
                 * @param msg 消息对象
                 * @param orderId 业务标识的参数（就是该函数传递的第三个参数）
                 * @return 所选择的队列
                 */
                @Override
                public MessageQueue select(List<MessageQueue> queues, Message msg, Object orderId) {
                    Long index = (Long) orderId % queues.size();
                    return queues.get(index.intValue());
                }
            }, order.getOrderId());

            System.out.println("发送消息：" + sendResult);

            Thread.sleep(1000);
        }

        //5.关闭producer
        producer.shutdown();

    }
}
