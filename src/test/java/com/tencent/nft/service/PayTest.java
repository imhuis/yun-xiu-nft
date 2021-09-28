package com.tencent.nft.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: imhuis
 * @date: 2021/9/28
 * @description:
 */
@SpringBootTest
public class PayTest {



    @Autowired
    private IPayService payService;

    @Test
    public void notifyWx(){
        String nt = "{\"id\":\"311fe73f-c397-5e5a-9c8f-9fc94afb387b\",\"create_time\":\"2021-09-28T13:50:48+08:00\",\"resource_type\":\"encrypt-resource\",\"event_type\":\"TRANSACTION.SUCCESS\",\"summary\":\"支付成功\",\"resource\":{\"original_type\":\"transaction\",\"algorithm\":\"AEAD_AES_256_GCM\",\"ciphertext\":\"Ay25zutpHN+uMAaO6mqOeZvePpU3VR7wfY4GWlbI+bifz58DbR0BSrpI+kAmE/WnMbgwG5rBQKPzjZcfCIPILVGUMWMaPnGI7CzQ2RHuMXUzGugYH4iJZOjYVmxEJDB3J1OAPJoZC5Xu7b8aFrFbY/WqQ+pUWZnfBEkdyQPx7yHU06b8UEtYZjlbyPPP4XGe42DM6nuWiOmVvDxPtAO4XyZKqzW6WteLzHbF/kqa8f5VzkhXsJV25ytfV9/JM7/KW3nLOgz6KWfsK3K4UmlExK5OewSTGiTjM6pApyXuBSBLSMsI6efhQ6RVRcfcWs8lyDIXsc1BxW+Axvv4Y5NGfsSbBoxF1NhJT+8EScFwrMdOcO2prPFtLzNTsxjsPYTd/OGA2c1mgImjsJBMhSe/vkZW0bmXXsU/M/EBpzu72aXHbj/yVhjtJeG9k/RGT7ez7Z0m3+nIaViareifSm//IG/pHzAtCrULK+npNNHrvUajFKum4gYSMksiSq5RRmvQIs+vzg0DvsZXAP19w02/uNV8G5SefTXk5q5Z+JM5dYaWM7sP0TNDkWHssD9j0xZlnhZTWsBM/7Vmw3R0nvZR\",\"associated_data\":\"transaction\",\"nonce\":\"HipFwys1ToyA\"}}";

        payService.notifyApp(nt);
    }
}
