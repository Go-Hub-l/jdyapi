package com.xlb.api.openplatform;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.xlb.TableStructRequest;
import com.xlb.api.AbstractApiClient;
import com.xlb.api.ApplicationIDClient;
import com.xlb.api.WidgetIDClient;
import com.xlb.conststr.APIURLConst;
import com.xlb.model.WidgetResponse;
import com.xlb.model.widget.reimbursement.ReimbursementRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OpenPlatformWidgetClient extends AbstractApiClient {
    @Autowired
    private ApplicationIDClient applicationIDClient;

    @Autowired
    private WidgetIDClient widgetIDClient;

    @Data
    public static class ReimburseData{
        private String username;
        private String date;
        private String amount;
        private String desc;
    }

    @Override
    protected String getCurrentApplicationID() {
        return applicationIDClient.getOpenPlatformAppId();
    }

    @Override
    protected String getCurrentEntryID() {
        return widgetIDClient.getReimbursementEntryId();
    }

    @Override
    protected String getRequestData(String obj) {
        Assert.notBlank(obj, "插入数据时，obj不能为空");
        ReimburseData reimburseData = JSONUtil.toBean(obj, ReimburseData.class);
        Assert.notNull(reimburseData, "插入数据时，obj转换失败");
        Assert.notBlank(reimburseData.getAmount(), "数据有问题");

        ReimbursementRequest form = new ReimbursementRequest();
        form.setApp_id(getCurrentApplicationID());
        form.setEntry_id(getCurrentEntryID());

        form.setData(new HashMap<>());
        form.getData().put("_widget_1727145447534", new HashMap<>(){{put("value", reimburseData.getUsername());}});
        form.getData().put("_widget_1727145447535", new HashMap<>(){{put("value", reimburseData.getDate());}});
        form.getData().put("_widget_1727145447537", new HashMap<>(){{put("value", reimburseData.getAmount());}});
        form.getData().put("_widget_1727145447536", new HashMap<>(){{put("value", reimburseData.getDesc());}});
        //这个提交人的数据设置的有问题
        form.getData().put("creator", new HashMap<>(){{put("value", reimburseData.getUsername());}});
        form.getData().put("data_creator", new HashMap<>(){{put("value", reimburseData.getUsername());}});

        return JSONUtil.toJsonStr(form);
    }

    public void insert(String obj) {
        String response = sendPostRequestAndGetResponse(APIURLConst.CURRENT_FORM_CREATE_DATA_URL, obj);
        System.out.println(response);
    }
    //    public WidgetResponse.Widget getReimbursementWidget() {
//
//    }
}
