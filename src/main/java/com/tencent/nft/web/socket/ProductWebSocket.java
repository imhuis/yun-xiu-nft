package com.tencent.nft.web.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.net.http.WebSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
@Controller
@ServerEndpoint("/app/public/ws/market/{nft_id}")
@Slf4j
public class ProductWebSocket {

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    private static ConcurrentHashMap<String, ProductWebSocket> webSocketSet = new ConcurrentHashMap<>();

    private Session session;
    private String name;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nft_id") String name) {
        this.session = session;
        this.name = name;
//        webSocketSet.put(name, this);
        onlineCount.incrementAndGet();

        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
//        webSocketSet.remove(this.name);
        onlineCount.decrementAndGet();
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法,客户端发送过来的消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nft_id") String ntfId) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        this.sendMessage("Hello, " + message, session, ntfId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession, String ntfId) {

        StringBuilder sb = new StringBuilder("yy:");
        String key = sb.append(ntfId.trim().toLowerCase()).toString();
        long size = redisTemplate.boundSetOps(key).size();
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message + size);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e);
        }
    }

}
