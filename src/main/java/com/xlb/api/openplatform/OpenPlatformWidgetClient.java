package com.xlb.api.openplatform;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.xlb.api.AbstractApiClient;
import com.xlb.api.ApplicationIDClient;
import com.xlb.api.WidgetIDClient;
import com.xlb.conststr.APIURLConst;
import com.xlb.entity.widget.reimbursement.ReimbursementRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<ReimburseData> reimburseData = JSONUtil.toList(obj, ReimburseData.class);
        Assert.notEmpty(reimburseData, "插入数据时，obj转换失败");

        ReimbursementRequest form = new ReimbursementRequest();
        form.setApp_id(getCurrentApplicationID());
        form.setEntry_id(getCurrentEntryID());
        //默认是第一个报销人
        form.setData_creator(reimburseData.get(0).getUsername());

        form.setData_list(new ArrayList<>());

        List<Map<String, Object>> dataList = form.getData_list();
        for (ReimburseData reimburse : reimburseData) {
            Map<String, Object> data = new HashMap<>();
            data.put("_widget_1727145447534", new HashMap<String, String>(){{put("value", reimburse.getUsername());}});
            data.put("_widget_1727145447535", new HashMap<String, String>(){{put("value", reimburse.getDate());}});
            data.put("_widget_1727145447537", new HashMap<String, String>(){{put("value", reimburse.getAmount());}});
            data.put("_widget_1727145447536", new HashMap<String, String>(){{put("value", reimburse.getDesc());}});

            dataList.add(data);
        }


        return JSONUtil.toJsonStr(form);
    }

    public void insertBatch(String obj) {
        String response = sendPostRequestAndGetResponse(APIURLConst.CURRENT_FORM_CREATE_DATA_LIST_URL, obj);
        System.out.println(response);
    }
}
