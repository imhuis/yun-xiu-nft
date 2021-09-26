package com.tencent.nft.core.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zyixh
 * @date: 2020/4/6
 * @description:
 */
@Configuration
public class RabbitmqConfig {

    /**
     * 交换机名称
     */
    public static final String WX_NOTIFY_QUEUE_NAME = "wx-notify-queue";

    /**
     * 死信队列 交换机标识符
     * 死信队列交换机routing-key标识符
     * 死信队列消息的超时时间枚举
     */
    private static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    private static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    private static final String X_MESSAGE_TTL = "x-message-ttl";


    @Bean
    public Queue queueSxl(){
        // 持久化
        boolean durable = true;
        // 仅创建者可以使用私有队列
        boolean exclusive = false;
        // 自动删除队列
        boolean autoDelete = false;
        return new Queue(WX_NOTIFY_QUEUE_NAME);
    }

}
