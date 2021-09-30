package com.tencent.nft.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.nft.common.enums.pay.TradeState;
import com.tencent.nft.common.exception.business.PayException;
import com.tencent.nft.common.util.MoneyUtil;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.common.util.WxPayUtil;
import com.tencent.nft.core.config.RabbitmqConfig;
import com.tencent.nft.common.properties.WxGroupProperties;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.pay.PreOrder;
import com.tencent.nft.entity.pay.bo.OrderMessageBO;
import com.tencent.nft.entity.pay.dto.PayRequestDTO;
import com.tencent.nft.entity.pay.TradeInfo;
import com.tencent.nft.entity.pay.bo.PayDetailBO;
import com.tencent.nft.entity.pay.bo.PrepayVO;
import com.tencent.nft.entity.security.WxUser;
import com.tencent.nft.mapper.pay.ProductMapper;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.mapper.pay.OrderMapper;
import com.tencent.nft.mapper.pay.TradeMapper;
import com.tencent.nft.service.IPayService;
import com.tencent.nft.service.handler.WeChatPayHandler;
import com.tencent.nft.service.pay.IStockCallback;
import com.tencent.nft.service.pay.StockService;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.PrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: imhuis
 * @date: 2021/9/27
 * @description:
 */
@Service
public class PayServiceImpl implements IPayService {

    final Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private WeChatPayHandler payHandler;

    @Autowired
    private WxGroupProperties wxGroupProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private TradeMapper tradeMapper;

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StockService stockService;

    @Override
    public PrepayVO order(PayRequestDTO dto) throws Exception {
        // 首先判断是否有购买资格，已经预约，或者购买买过一次的不能购买第二次
        String openId = dto.getOpenId();
        final String productId = dto.getProductId().toLowerCase().trim();
        WxUser wxUser = wxUserMapper.selectFullByOpenId(openId).get();

        // 检查是否预约
        boolean flag = stringRedisTemplate.opsForSet().isMember("yy:" + productId, wxUser.getPhone());
        if (!flag) {
            throw new PayException("抱歉,您未预约该商品！");
        }
        boolean flag1 = stringRedisTemplate.opsForSet().isMember("gm:" + productId, openId);
        if (flag1) {
            throw new PayException("重复购买！");
        }

        String redisKey = "product:stock:" + productId;
        log.info("初始库存 {}", productMapper.selectStock(productId));

        long stock = stockService.stock(redisKey, 60 * 60, 1, () -> productMapper.selectStock(productId));
        log.info("剩余库存 {}", stock);
        if (stock >= 0) {
//            final String tradeNo = UUIDUtil.generateUUID();
//
//            PayDetailBO payDetailBO = createPayDetailBO(tradeNo, dto);
//            // 生成prepay_id
//            String prepayId = payHandler.handler(payDetailBO);
//            // 生产预订单。插入数据表
//            createPreOrder(payDetailBO);
//            return createOrder(prepayId);
        }

//        else {
//            throw new PayException("");
//        }
        return null;


        /**
         * 已经售罄
         */
//        int stock = productMapper.selectStock(dto.getProductId());
//        if (stock < 0){
//            throw new PayException("商品已售罄！");
//        }

        /**
         * 生成内部业务流水
         */
//        final String tradeNo = UUIDUtil.generateUUID();
//        PayDetailBO payDetailBO = createPayDetailBO(tradeNo, dto);
//        System.out.println(payDetailBO.getTotal());
        // 生成prepay_id
//        String prepayId = payHandler.handler(payDetailBO);
        // 生产预订单。插入数据表
//        createPreOrder(payDetailBO);
    }


    PrepayVO createOrder(String prepayId) throws IOException {
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        final String nonceStr = RandomUtil.randomString(32);
        final String packages = "prepay_id=" + prepayId;

        StringBuilder sb = new StringBuilder();
        sb.append(wxGroupProperties.getAppletAppId() + "\n")
                .append(timestamp + "\n")
                .append(nonceStr + "\n")
                .append(packages + "\n");
        String result = sb.toString();

        ClassPathResource classPathResource = new ClassPathResource("pay/apiclient_key.pem");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(classPathResource.getInputStream());

        String sign = WxPayUtil.getPaySign(result, merchantPrivateKey);
        log.info("PaySign {}", sign);

        // 小程序调起支付API
        PrepayVO prepayVO = new PrepayVO();
        prepayVO.setAppId(wxGroupProperties.getAppId());
        prepayVO.setTimeStamp(timestamp);
        prepayVO.setNonceStr(nonceStr);
        prepayVO.setPrepayId(prepayId);
        prepayVO.setPackageStr(packages);
        prepayVO.setSignType("RSA");
        prepayVO.setPaySign(sign);
        return prepayVO;

    }

    private PayDetailBO createPayDetailBO(String tradeNo, PayRequestDTO dto) {
        // 查询商品信息
        NFTProduct product = productMapper.selectByNftId(dto.getProductId()).get();
        PayDetailBO payDetailBO = new PayDetailBO();
        payDetailBO.setTradeNo(tradeNo);
        payDetailBO.setOpenId(dto.getOpenId());
        payDetailBO.setTotal(MoneyUtil.yuan2fen(product.getUnitPrice()));
        payDetailBO.setDescription("数字藏品-" + dto.getProductId());
        // 重要
        payDetailBO.setProductNo(product.getNftId());
        return payDetailBO;
    }

    @Override
    public void notifyApp(String resJson) {

        /**
         * {
         *     "id": "311fe73f-c397-5e5a-9c8f-9fc94afb387b",
         *     "create_time": "2021-09-28T13:50:48+08:00",
         *     "resource_type": "encrypt-resource",
         *     "event_type": "TRANSACTION.SUCCESS",
         *     "summary": "支付成功",
         *     "resource": {
         *         "original_type": "transaction",
         *         "algorithm": "AEAD_AES_256_GCM",
         *         "ciphertext": "Ay25zutpHN+uMAaO6mqOeZvePpU3VR7wfY4GWlbI+bifz58DbR0BSrpI+kAmE/WnMbgwG5rBQKPzjZcfCIPILVGUMWMaPnGI7CzQ2RHuMXUzGugYH4iJZOjYVmxEJDB3J1OAPJoZC5Xu7b8aFrFbY/WqQ+pUWZnfBEkdyQPx7yHU06b8UEtYZjlbyPPP4XGe42DM6nuWiOmVvDxPtAO4XyZKqzW6WteLzHbF/kqa8f5VzkhXsJV25ytfV9/JM7/KW3nLOgz6KWfsK3K4UmlExK5OewSTGiTjM6pApyXuBSBLSMsI6efhQ6RVRcfcWs8lyDIXsc1BxW+Axvv4Y5NGfsSbBoxF1NhJT+8EScFwrMdOcO2prPFtLzNTsxjsPYTd/OGA2c1mgImjsJBMhSe/vkZW0bmXXsU/M/EBpzu72aXHbj/yVhjtJeG9k/RGT7ez7Z0m3+nIaViareifSm//IG/pHzAtCrULK+npNNHrvUajFKum4gYSMksiSq5RRmvQIs+vzg0DvsZXAP19w02/uNV8G5SefTXk5q5Z+JM5dYaWM7sP0TNDkWHssD9j0xZlnhZTWsBM/7Vmw3R0nvZR",
         *         "associated_data": "transaction",
         *         "nonce": "HipFwys1ToyA"
         *     }
         * }
         */
        // 读取json树
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(resJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /**
         *
         *  {
         *     "mchid": "1503971171",
         *     "appid": "wxb3982d59b8a5e644",
         *     "out_trade_no": "751b1db31b6240748aefc580cd674307",
         *     "transaction_id": "4200001121202109282704065018",
         *     "trade_type": "JSAPI",
         *     "trade_state": "SUCCESS",
         *     "trade_state_desc": "支付成功",
         *     "bank_type": "OTHERS",
         *     "attach": "",
         *     "success_time": "2021-09-28T13:50:48+08:00",
         *     "payer": {
         *         "openid": "oQ13L5K2HVK3rLYcIlw_n2va7Tcs"
         *     },
         *     "amount": {
         *         "total": 1,
         *         "payer_total": 1,
         *         "currency": "CNY",
         *         "payer_currency": "CNY"
         *     }
         * }
         *
         */

        String eventType = jsonNode.get("event_type").asText();
        // 支付成功
        if (eventType.equals("TRANSACTION.SUCCESS")) {
            JsonNode childField = jsonNode.get("resource");
            String nonce = childField.get("nonce").asText();
            String associatedData = childField.get("associated_data").asText();
            String ciphertext = childField.get("ciphertext").asText();
            String resourceJson = WxPayUtil.decryptNotifyV3(associatedData, nonce, ciphertext, wxGroupProperties.getApiKey());

            JsonNode tradeDetail = null;
            try {
                tradeDetail = objectMapper.readTree(resourceJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            log.info("resource \n {}", tradeDetail.toString());

            // 外部订单号码
            String outTradeNo = tradeDetail.get("out_trade_no").asText();

            PreOrder preOrder = orderMapper.selectByTradeNo(outTradeNo);
            String productNo = preOrder.getProductNo();
            OrderMessageBO orderMessageBO = new OrderMessageBO(outTradeNo, preOrder.getPayer(), productNo);
            String message = Strings.EMPTY;
            try {
                message = objectMapper.writeValueAsString(orderMessageBO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            // 系统内部流水号判断支付结果
            TradeInfo t = tradeMapper.selectByTradeNo(outTradeNo);
            if (t == null) {
                t = new TradeInfo();
                t.setTradeNo(outTradeNo);
                t.setTransactionId(tradeDetail.get("transaction_id").asText());
                // 设置价格，单位为分
                t.setAmount(tradeDetail.at("/amount/total").asInt());
                t.setPayerTotal(tradeDetail.at("/amount/payer_total").asInt());
                // 支付人
                t.setPayer(tradeDetail.at("/payer/openid").asText());
                t.setTradeStatus(TradeState.SUCCESS);
                tradeMapper.updateByTradeNo(t);
            } else {
                if (t.getTradeStatus() == TradeState.SUCCESS) {
                    amqpTemplate.convertAndSend(RabbitmqConfig.DEFAULT_EXCHANGE_NAME, RabbitmqConfig.PAY_NOTIFY_ROUTE_KEY, message);
                    return;
                }
                t.setTradeNo(outTradeNo);
                t.setTransactionId(tradeDetail.get("transaction_id").asText());
                // 设置价格，单位为分
                t.setAmount(tradeDetail.at("/amount/total").asInt());
                t.setPayerTotal(tradeDetail.at("/amount/payer_total").asInt());
                // 支付人
                t.setPayer(tradeDetail.at("/payer/openid").asText());
                t.setTradeStatus(TradeState.SUCCESS);
                tradeMapper.updateByTradeNo(t);
            }
            amqpTemplate.convertAndSend(RabbitmqConfig.DEFAULT_EXCHANGE_NAME, RabbitmqConfig.PAY_NOTIFY_ROUTE_KEY, message);

        }

    }

    @Override
    public void refund() {

    }

    @Override
    public void downloadBill() {

    }

    @Transactional
    @Async
    void updateUserLibrary() {

        // 更新数量
//        productMapper.updateStockByProductId();
//        libraryMapper.selectNftIdByPhone();

    }


    @Transactional
    @Async
    public void createPreOrder(PayDetailBO payDetailBO) {
        String outTradeNo = payDetailBO.getTradeNo();
        // 预订单默认未付款的订单
        PreOrder preOrder = new PreOrder();
        preOrder.setTradeNo(outTradeNo);
        preOrder.setProductNo(payDetailBO.getProductNo());
        preOrder.setPrice(payDetailBO.getTotal());
        preOrder.setPayer(payDetailBO.getOpenId());
        orderMapper.insert(preOrder);

        // 默认新增状态为未付款状态订单
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeNo(outTradeNo);
        tradeInfo.setTradeStatus(TradeState.NOTPAY);
        tradeInfo.setAmount(payDetailBO.getTotal());
        tradeInfo.setDescription(payDetailBO.getDescription());
        tradeInfo.setPayer(payDetailBO.getOpenId());
        tradeMapper.insert(tradeInfo);
    }

}
