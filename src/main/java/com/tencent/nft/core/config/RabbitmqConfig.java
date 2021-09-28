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
    public static final String ON_CHAIN_QUEUE_NAME = "on-chain-queue";


    public static final String DEFAULT_EXCHANGE_NAME = "default-exchange";

    public static final String WX_NOTIFY_ROUTE_KEY = "nft.topic.wxpay";

    public static final String ON_CHAIN_ROUTE_KEY = "nft.topic.onchain";

    /**
     * 死信队列 交换机标识符
     * 死信队列交换机routing-key标识符
     * 死信队列消息的超时时间枚举
     */
    private static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    private static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    private static final String X_MESSAGE_TTL = "x-message-ttl";

    /**
     * 默认交换机
     * @return
     */
    @Bean
    public TopicExchange defaultExchange(){
        return new TopicExchange(DEFAULT_EXCHANGE_NAME, true, false);
    }


    @Bean
    public Queue wxNotifyQueue(){
        return new Queue(WX_NOTIFY_QUEUE_NAME);
    }

    @Bean
    public Queue onChain(){
        return new Queue(ON_CHAIN_QUEUE_NAME);
    }

    @Bean
    public Binding topicBinding1(@Qualifier("wxNotifyQueue") Queue queue, @Qualifier("defaultExchange") TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(WX_NOTIFY_ROUTE_KEY);
    }

    @Bean
    public Binding topicBinding2(@Qualifier("onChain") Queue queue, @Qualifier("defaultExchange") TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(ON_CHAIN_ROUTE_KEY);
    }

}
