package com.tencent.nft.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.nft.common.base.ResponseResult;
import com.tencent.nft.common.base.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@RestController
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);


    @RequestMapping("/public/hello")
    public ResponseResult<String> hello(){
        return ResponseUtil.success("Hello World!");
    }

    @RequestMapping("/demo/hello")
    public ResponseResult<String> hi(){
        return ResponseUtil.success("Hi World!");
    }

    @RequestMapping("/app/hello")
    public ResponseResult say(){
        return ResponseUtil.success("Hello. This is app.");
    }

    @RequestMapping("/public/time1/{date}")
    public ResponseResult whatIsTime(@PathVariable LocalDate date){
        logger.info(date.toString());
        return ResponseUtil.success();
    }

    @RequestMapping("/public/time2/{date}")
    public ResponseResult whatIsTimes(DateObject object) throws JsonProcessingException {
        String x = new ObjectMapper().writeValueAsString(object);
        logger.info(x);
        return ResponseUtil.success();
    }

}

class DateObject {
    private Date date;
    private LocalDate localDate;
    private LocalDateTime localDateTime;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
