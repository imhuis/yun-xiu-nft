package com.tencent.nft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: imhuis
 * @date: 2021/1/1
 * @description:
 */
@SpringBootApplication
public class NFTApplication {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(NFTApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(NFTApplication.class, args);
    }

}
