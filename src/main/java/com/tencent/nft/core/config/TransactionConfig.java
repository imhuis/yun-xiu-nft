package com.tencent.nft.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Configuration
@EnableTransactionManagement
@EnableAsync
public class TransactionConfig {
}
