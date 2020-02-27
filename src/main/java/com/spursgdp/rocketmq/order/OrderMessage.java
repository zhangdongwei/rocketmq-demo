package com.spursgdp.rocketmq.order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangdongwei
 * @create 2020-02-27-14:19
 */
public class OrderMessage {


    private Long orderId;

    private String desc;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "orderId='" + orderId + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static List<OrderMessage> buildOrders() {
        //1039L : 创建 付款 推送 完成（共产生4条记录）
        //1065L : 创建 付款 （共产生2条记录）
        //7235L : 创建 付款 推送 （共产生3条记录）

        ArrayList<OrderMessage> orders = new ArrayList<>();

        OrderMessage order = new OrderMessage();
        order.setOrderId(1039L);
        order.setDesc("创建");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(1065L);
        order.setDesc("创建");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(7235L);
        order.setDesc("创建");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(1039L);
        order.setDesc("付款");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(1065L);
        order.setDesc("付款");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(7235L);
        order.setDesc("付款");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(1039L);
        order.setDesc("推送");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(7235L);
        order.setDesc("推送");
        orders.add(order);

        order = new OrderMessage();
        order.setOrderId(1039L);
        order.setDesc("完成");
        orders.add(order);

        return orders;

    }
}
