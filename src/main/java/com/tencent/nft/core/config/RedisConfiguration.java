package com.tencent.nft.core.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import java.time.Duration;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Configuration
public class RedisConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory lettuceConnectionFactory(){
//        RedisClusterConfiguration redisClusterConnection = new RedisClusterConfiguration();
//        redisClusterConnection.addClusterNode(new RedisNode("redis1",26379));
//        redisClusterConnection.addClusterNode(new RedisNode("redis2",26379));
//        redisClusterConnection.addClusterNode(new RedisNode("redis3",26379));
//        redisClusterConnection.setMaxRedirects(3);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());

        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh(Duration.ofSeconds(60))
                .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.ASK_REDIRECT, ClusterTopologyRefreshOptions.RefreshTrigger.UNKNOWN_NODE)
                .build();

        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)// 拓扑刷新
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .socketOptions(SocketOptions.builder().keepAlive(true).build())
                .validateClusterNodeMembership(false)// 取消校验集群节点的成员关系
                .build();

        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
//                .clientOptions(clusterClientOptions)
//                .readFrom(ReadFrom.ANY)
                .commandTimeout(Duration.ofMillis(1000))
                .poolConfig(genericObjectPoolConfig())
                .build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }


    public GenericObjectPoolConfig genericObjectPoolConfig(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMinIdle(5);
        genericObjectPoolConfig.setMaxIdle(10);
        genericObjectPoolConfig.setMaxTotal(20);
        return genericObjectPoolConfig;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

//    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
