package com.xlb.api;

import cn.hutool.core.lang.Assert;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WidgetIDClientTest {
    @Autowired
    private WidgetIDClient widgetIDClient;

    @Test
    public void testGetReimbursementEntryId() {
        String reimbursementEntryId = widgetIDClient.getReimbursementEntryId();

        Assert.notBlank(reimbursementEntryId);
    }
}
