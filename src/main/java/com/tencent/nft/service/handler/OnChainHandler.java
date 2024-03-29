package com.tencent.nft.service.handler;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;
import com.tencent.nft.common.properties.ChainProperties;
import com.tencent.nft.common.util.CodeUtil;
import com.tencent.nft.common.util.OKHttpClientBuilder;
import com.tencent.nft.entity.chain.ChainAddressResult;
import com.tencent.nft.entity.chain.CreateData;
import com.tencent.nft.entity.chain.GetAccessTokenResult;
import com.tencent.nft.entity.chain.OnChainResponse;
import net.sf.json.JSONObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description: 消息队列上链服务
 */
@Component
public class OnChainHandler {

    Logger log = LoggerFactory.getLogger(OnChainHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    // 监听上链消息
    @RabbitListener(queues = {"on-chain-queue"})
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


    /**
     * btoe获取access_token
     * @return
     */
    public String getAccessToken() {
        String url = "https://btoe.tusi.tencent-cloud.net/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.set("client_id", "2d6caf8f87bb40e1919a13ffb0773b7f");
        map.set("client_secret", "r9qV4etaNIig8FPKVtDMxzfldksDai3LLq0zjS6T");
        map.set("grant_type", "client_credentials");
        map.set("scope", "read write");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseBody = restTemplate.postForEntity(url, request, String.class);

        JSONObject jsonObject = JSONObject.fromObject(responseBody.getBody());
        String accessToken = (String)jsonObject.get("access_token");
        return accessToken;
    }

    /**
     * btoe获取access_token BTOE写⼊待存证的业务数据明文，业务数据明文存证写⼊后不可修改，BTOE对业务数据明文存证生成含有电⼦签章的区块链存证电⼦凭证
     * @param createData
     * @param accessToken
     * @return
     * @throws JsonProcessingException
     */
    public String createDataDeposit(CreateData createData, String accessToken) throws JsonProcessingException {
        String url = "https://btoe.tusi.tencent-cloud.net/v1/api/normalDeposit";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "bearer" + accessToken);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("InterfaceName", "CreateDataDeposit");
        map.put("Data", createData);
        String requestJson = new ObjectMapper().writeValueAsString(map);
        log.info("业务数据明文存证 {}", requestJson);

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(url, request, String.class);
        /** {"Response":{"RequestId":"a28c5fdc-742b-4787-992d-46f772d08215","EvidenceId":"a28c5fdc-742b-4787-992d-46f772d08215","BusinessId":null}} **/
        String response = responseEntityStr.getBody();
        log.info("业务数据返回 {}", response);
        JsonNode jsonNode = objectMapper.readTree(response);
        String a = jsonNode.at("/Response/EvidenceId").asText();
//        log.info("jsonNode.get(\"Response\").asText() {}", response);

//        OnChainResponse onChainResponse = objectMapper.readValue(response, OnChainResponse.class);
        return a;

    }

    public String getDepositInfo(String evidenceId, String accessToken) throws JsonProcessingException {
        String url = "https://btoe.tusi.tencent-cloud.net/v1/api/action";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "bearer" + accessToken);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("InterfaceName", "GetDepositInfo");
        Map<String,String> params = new LinkedHashMap<>();
        params.put("EvidenceId", evidenceId);
        map.put("Data", params);
        String requestJson = new ObjectMapper().writeValueAsString(map);
        log.info("请求参数 {}", requestJson);

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(url, request, String.class);
        String response = responseEntityStr.getBody();
        /** {"Response":{"RequestId":"a28c5fdc-742b-4787-992d-46f772d08215","EvidenceId":"a28c5fdc-742b-4787-992d-46f772d08215","BusinessId":null}} **/
        log.info("请求返回 {}", response);
        JsonNode jsonNode = objectMapper.readTree(responseEntityStr.getBody());

        return "";
//        ChainAddressResult onChainResponse = objectMapper.readValue(response, ChainAddressResult.class);
//        return onChainResponse.getEvidenceTxHash();

    }

}
