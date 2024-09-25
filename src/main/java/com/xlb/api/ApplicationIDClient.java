package com.xlb.api;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.xlb.conststr.APIURLConst;
import com.xlb.model.application.ApplicationResponse;
import org.springframework.stereotype.Component;


/**
 * 获取应用ID的客户端
 */
@Component
public class ApplicationIDClient extends AbstractApiClient{

    private final String APPLICATIONS;
    private final ApplicationResponse APPLICATIONRESPONSE;
    {
        APPLICATIONS = sendPostRequestAndGetResponse(APIURLConst.APPLICATION_URL, "");
        APPLICATIONRESPONSE = JSONUtil.toBean(APPLICATIONS, ApplicationResponse.class);

        Assert.notEmpty(APPLICATIONS, "获取应用ID string为空");
        Assert.notNull(APPLICATIONRESPONSE, "获取应用ID失败");
        Assert.notEmpty(APPLICATIONRESPONSE.getApps(), "获取应用ID为空");
    }

    public String getOpenPlatformAppId() {
        return getAppId("开放平台");
    }

    private String getAppId(String appName) {
        String appid = APPLICATIONRESPONSE.getApps()
                .stream()
                .filter(app -> appName.equals(app.getName()))
                .map(ApplicationResponse.Application::getApp_id)
                .findFirst()
                .orElse("");

        return appid;
    }
}
