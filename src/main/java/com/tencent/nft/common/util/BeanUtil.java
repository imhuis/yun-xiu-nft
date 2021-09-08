package com.tencent.nft.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class BeanUtil {

    public Map<String, Object> beanToMap(Object o){

        return cn.hutool.core.bean.BeanUtil.beanToMap(o);
    }

    public Map<String, Object> JacksonBeanToMap(Object o) throws JsonProcessingException {
        return new ObjectMapper().readValue(objectToString(o), Map.class);
    }

    public Object JacksonMapToBean(Map map, Class clazz) throws JsonProcessingException {
        return new ObjectMapper().readValue(objectToString(map), clazz);
    }

    public static String objectToString(Object o) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(o);
    }
}
