package com.imhui.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
//@Configuration
//@EnableRedisHttpSession
public class HttpSessionConfig implements BeanClassLoaderAware {

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Bean
    public RedisSerializer<Object> serializer(){
        return new GenericJackson2JsonRedisSerializer(objectMapper());
    }

    private ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(this.classLoader));
        return objectMapper;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }


}
