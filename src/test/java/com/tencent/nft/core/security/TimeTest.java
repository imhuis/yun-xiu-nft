package com.tencent.nft.core.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class TimeTest {

    @Test
    public void test(){
        Duration expires = Duration.ofMillis(30);
//        System.out.println(expires.getSeconds());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

        System.out.println(fmt.parse("2021-09-28T05:33:45.000Z"));

    }

}
