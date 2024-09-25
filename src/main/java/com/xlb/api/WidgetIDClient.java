package com.xlb.api;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.xlb.conststr.APIURLConst;
import com.xlb.model.WidgetResponse;
import com.xlb.model.application.ApplicationResponse;
import com.xlb.model.widget.reimbursement.WidgetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 获取应用ID的客户端
 */
@Component
public class WidgetIDClient extends AbstractApiClient{
    @Autowired
    private ApplicationIDClient applicationIDClient;

    /**
     * 报销表单
     * @return
     */
    public String getReimbursementEntryId() {
        return getEntryId("报销明细");
    }

    @Override
    protected String getRequestData(String obj) {
        //开放平台ID
        String openPlatformAppId = applicationIDClient.getOpenPlatformAppId();

        WidgetRequest widgetRequest = new WidgetRequest();
        widgetRequest.setApp_id(openPlatformAppId);

        return JSONUtil.toJsonStr(widgetRequest);
    }

    private String getEntryId(String appName) {

        String response = sendPostRequestAndGetResponse(APIURLConst.CURRENT_APPLICATION_FORM_URL, "");
        WidgetResponse widgetResponse = JSONUtil.toBean(response, WidgetResponse.class);

        Assert.notNull(widgetResponse, "获取" + appName + "ID失败");

        String entryId = widgetResponse.getForms()
                .stream().filter(widget -> appName.equals(widget.getName()))
                .map(WidgetResponse.Widget::getEntry_id)
                .findFirst()
                .orElse("");

        return entryId;
    }
}
