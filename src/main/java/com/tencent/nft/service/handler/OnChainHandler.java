package com.tencent.nft.service.handler;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description: 消息队列上链服务
 */
public class OnChainHandler {

    Logger log = LoggerFactory.getLogger(OnChainHandler.class);

    // 监听上链消息
    @RabbitListener(queues = {"on-chain-queue-sxl"})
    public void onChain(Message message, Channel channel) throws IOException {

        try {
            String queue = message.getMessageProperties().getConsumerQueue();
            log.info("接收来自{}队列的消息", queue);
            // 处理
            String messageData = new String(message.getBody());

        } catch (Exception e) {
//            long retryCount = getRetryCount(message.getMessageProperties());
//            if (retryCount >= 3) {
//                //重试次数超过3次,则将消息发送到失败队列等待特定消费者处理或者人工处理
//                try {
//                    rabbitTemplate.convertAndSend(RabbitmqConfig.FAIL_EXCHANGE_NAME, "iot.fail.msg", message);
//                    log.info("消费者消费消息在重试3次后依然失败，将消息发送到failed队列,发送消息:" + new String(message.getBody()));
//                } catch (Exception e1) {
//                    log.error("消息在发送到failed队列的时候报错:" + e1.getMessage() + ",原始消息:" + new String(message.getBody()));
//                }
//            } else {
//                try {
//                    //重试次数不超过3次,则将消息发送到重试队列等待重新被消费（重试队列延迟超时后信息被发送到相应死信队列重新消费，即延迟消费）
//                    rabbitTemplate.convertAndSend(RabbitmqConfig.RETRY_EXCHANGE_NAME, "iot.retry.msg", message);
//                    log.info("消费者消费失败，消息发送到重试队列;" + "原始消息：" + new String(message.getBody()) + ";第" + (retryCount + 1) + "次重试");
//                } catch (Exception e2) {
//                    log.error("消息发送到重试队列的时候，异常了:" + e2.getMessage() + ",重新发送消息");
//                }
//            }
        } finally {
            /**
             * 无论消费成功还是消费失败,都要手动进行ack,因为即使消费失败了,也已经将消息重新投递到重试队列或者失败队列
             * 如果不进行ack,生产者在超时后会进行消息重发,如果消费者依然不能处理，则会存在死循环
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

        // 上链

        String address = MD5.create().digestHex(RandomUtil.randomString(50));
        System.out.println(address);

    }

}
