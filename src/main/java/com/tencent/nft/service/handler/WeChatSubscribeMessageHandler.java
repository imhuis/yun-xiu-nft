package com.tencent.nft.service.handler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
public class WeChatSubscribeMessageHandler {


    /**
     * 处理
     */
    @RabbitListener(queues = {"wx-ys-message-queue"})
    public void handle(){

    }
}
