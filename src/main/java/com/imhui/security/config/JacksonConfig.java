package com.imhui.security.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .failOnUnknownProperties(false) // 反序列遇到不存在的key不报错
                .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .createXmlMapper(true)
                .configure(objectMapper());
    }

    /**
     * springboot 2.x 不要直接注入 ObjectMapper 会被MappingJackson2HttpMessageConverter使用
     * @return
     */
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 序列化所有属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        return objectMapper;
    }
}
