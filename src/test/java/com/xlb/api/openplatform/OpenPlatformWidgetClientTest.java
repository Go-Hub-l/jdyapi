package com.xlb.api.openplatform;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenPlatformWidgetClientTest {
    @Autowired
    private OpenPlatformWidgetClient openPlatformWidgetClient;

    @Test
    public void testInsert() {
        OpenPlatformWidgetClient.ReimburseData reimburseData = new OpenPlatformWidgetClient.ReimburseData();
        reimburseData.setUsername("R-06bw8Pn2");
        reimburseData.setDate("2024-09-23");
        reimburseData.setAmount("666");
        reimburseData.setDesc("测试数据2");
        openPlatformWidgetClient.insert(JSONUtil.toJsonStr(reimburseData));
    }
}
