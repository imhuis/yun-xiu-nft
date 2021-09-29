package com.tencent.nft.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.tencent.nft.common.enums.NFTSaleStatusEnum;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.UserLibrary;
import com.tencent.nft.entity.pay.bo.OrderMessageBO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.mapper.UserLibraryMapper;
import com.tencent.nft.mapper.WxUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author: imhuis
 * @date: 2021/9/29
 * @description:
 */
@Component
public class PaySuccessMessageHandler {

    final Logger log = LoggerFactory.getLogger(PaySuccessMessageHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Resource
    private UserLibraryMapper userLibraryMapper;

    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private NftMapper nftMapper;

    // 监听上链消息
//    @RabbitListener(queues = {"pay-notify-queue"})
    public void onChain(Message message, Channel channel) throws IOException {

        try {
            String queue = message.getMessageProperties().getConsumerQueue();
            log.info("接收来自{}队列的消息", queue);
            // 处理
            String messageData = new String(message.getBody());
            log.info(messageData);
            /** {"trade_no":"2bcdf9cbfa1940f8913bec7599114176","open_id":"oQ13L5K2HVK3rLYcIlw_n2va7Tcs","product_id":"YX20210929131719"} **/
            OrderMessageBO messageBO = objectMapper.readValue(messageData, OrderMessageBO.class);
            String tradeNo = messageBO.getTradeNo();

            UserLibrary userLibrary = userLibraryMapper.selectByTradeNo(tradeNo);
            if (userLibrary == null){
                createNew(messageBO);
            }else {
                return;
            }

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

    }

//    @Transactional
    public void createNew(OrderMessageBO messageBO){
        String openId = messageBO.getOpenId();
        String tradeNo = messageBO.getTradeNo();
        String productId = messageBO.getProductId();

        WxUser wxUser = wxUserMapper.selectFullByOpenId(openId).get();
        SubNFT newSubNFT = nftMapper.selectSubBftByStatus(productId);

        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setTradeNo(tradeNo);
        userLibrary.setUserId(wxUser.getUserId());
        userLibrary.setPhone(wxUser.getPhone());
        userLibrary.setOpenId(wxUser.getOpenId());
        userLibrary.setNftId(newSubNFT.getNftId());

        newSubNFT.setChainAddress(UUIDUtil.generateUUID());
        newSubNFT.setSoldTime(LocalDateTime.now());
        newSubNFT.setSaleStatus(NFTSaleStatusEnum.Sold);
        userLibraryMapper.insertUserLibrary(userLibrary);
//        nftMapper.updateSubNft(newSubNFT);

    }

}
