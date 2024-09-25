package com.xlb.api;

import cn.hutool.core.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationIDClientTest {
    @Autowired
    private ApplicationIDClient applicationIDClient;

    @Test
    public void testGetOpenPlatformAppId(){
        String openPlatformAppId = applicationIDClient.getOpenPlatformAppId();

        Assert.notBlank(openPlatformAppId);
    }
}
