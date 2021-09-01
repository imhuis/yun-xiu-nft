package com.imhui.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.imhui.security.repository")
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {

}
