package com.tencent.nft.service.handler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
@Component
public class WeChatSubscribeMessageHandler {


    /**
     * 处理预售信息队列
     */
    @RabbitListener(queues = {"wx-ys-message-queue"})
    public void handle(){

    }
}
