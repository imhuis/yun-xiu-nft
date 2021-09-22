package com.tencent.nft.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@SpringBootTest
public class TimeTest {

    @Test
    public void test(){
        Duration expires = Duration.ofMillis(30);
        System.out.println(expires.getSeconds());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        System.out.println(fmt.format(LocalDateTime.now()));
    }

}
