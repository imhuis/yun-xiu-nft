package com.tencent.nft.core.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class TimeTest {

    @Test
    public void test(){
        Duration expires = Duration.ofMillis(30);
        System.out.println(expires.getSeconds());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        System.out.println(fmt.format(LocalDateTime.now()));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD'T'HH:mm:ss+TIMEZONE");
        LocalDateTime dateTime = LocalDateTime.parse("2018-06-08T10:34:56+08:00", dateTimeFormatter);
    }

}
