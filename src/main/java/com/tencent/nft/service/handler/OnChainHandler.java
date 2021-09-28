package com.tencent.nft.service.handler;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;
import com.tencent.nft.common.properties.ChainProperties;
import com.tencent.nft.common.util.CodeUtil;
import com.tencent.nft.entity.chain.GetAccessTokenResult;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description: 消息队列上链服务
 */
public class OnChainHandler {

    Logger log = LoggerFactory.getLogger(OnChainHandler.class);

    @Autowired
    private ChainProperties chainProperties;

    @Autowired
    private RestTemplate restTemplate;

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


    public String getChainAddress() {
        // 先从缓存获取token, 如果未登录调用 getAccessToken()

        // 上链操作

        return "";
    }

    private String onChain(String evidenceType,
                           String evidenceId,
                           String hashType,
                           String evidenceInfo,
                           String accessToken) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        long currentTime = System.currentTimeMillis();
        String noncestr = "0123456789012345678901234567890123456789";

        Map<String, String> map = new HashMap();
        map.put("evidenceType", evidenceType);
        map.put("evidenceId", evidenceId);
        map.put("hashType", hashType);
        map.put("evidenceInfo", evidenceInfo);
        map.put("appId", "");
        map.put("noncestr", noncestr);
        map.put("timestamp", String.valueOf(currentTime));
        System.out.println(map);
        String sign = CodeUtil.createSignature(map, chainProperties.getClientSecret());
        System.out.println(sign);

        RequestBody requestBody = new FormBody.Builder()
                .add("evidenceType", evidenceType)
                .add("evidenceId", evidenceId)
                .add("hashType", hashType)
                .add("evidenceInfo", evidenceInfo)
                .add("appId", "")
                .add("noncestr", noncestr)
                .add("timestamp", String.valueOf(currentTime))
                .add("sign", sign).build();

        URI uri = UriComponentsBuilder.fromUriString("" + "/v1/onchain/evidence").build().toUri();
        RequestEntity<Void> requestEntity = RequestEntity
                .post(uri)
                .header("Authorization", "bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<String> responseBody = restTemplate.exchange(requestEntity, String.class);
        return responseBody.getBody();
    }


    private String getAccessToken(String scope) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("client_id", chainProperties.getClientId())
                .add("client_secret", chainProperties.getClientSecret())
                .add("grant_type", "client_credentials")
                .add("scope", scope).build();

        URI uri = UriComponentsBuilder.fromUriString("" + "/oauth/token").build().toUri();
        RequestEntity<Void> requestEntity = RequestEntity
                .post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<GetAccessTokenResult> result = restTemplate.exchange(requestEntity, GetAccessTokenResult.class);
        if (result.getStatusCode().is2xxSuccessful()) {
            return result.getBody().getAccessToken();
        }
        return "";
    }

}
