package com.xlb.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.xlb.api.openplatform.OpenPlatformWidgetClient;
import com.xlb.conststr.ReimbursementConst;
import com.xlb.entity.reimbursement.ReimbursementData;
import com.xlb.syncapi.api.ReimbursementSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@DubboService
@Component
public class ReimbursementSync implements ReimbursementSyncService {
    @Autowired
    private OpenPlatformWidgetClient openPlatformWidgetClient;

    @Override
    public int insert(String obj) {
        if (!StringUtils.hasLength(obj)) {
            log.info("object is null or empty");
            return 0;
        }

        Map<String, Object> reimbursementData = JSONUtil.toBean(obj, Map.class);

        Assert.notEmpty(reimbursementData, "reimbursementData is null");

        Map<String, String> bxje = (Map<String, String>)reimbursementData.get(ReimbursementConst.BXJE);
        Map<String, String> sm = (Map<String, String>)reimbursementData.get(ReimbursementConst.SM);
        Map<String, String> fylx = (Map<String, String>)reimbursementData.get(ReimbursementConst.FYLX);
        Map<String, String> fyrq = (Map<String, String>)reimbursementData.get(ReimbursementConst.FYRQ);
        String bjr = (String)reimbursementData.get(ReimbursementConst.BXR);

        if (bxje == null || sm == null || fylx == null || fyrq == null || bjr == null) {
            log.info("reimbursementData is not valid");
            return 0;
        }
        int[] count = {0};
        //转换数据
        bxje.forEach((k, v) -> {
            OpenPlatformWidgetClient.ReimburseData reimburseData = new OpenPlatformWidgetClient.ReimburseData();
            reimburseData.setAmount(v);
            reimburseData.setDate(fyrq.getOrDefault(k, ""));
            reimburseData.setDesc(String.format("%s_%s", fylx.getOrDefault(k, "类型"), sm.getOrDefault(k, "无")));

            //根据姓名获取用户编号(todo):：这里先写死吧，后续知道如何获取员工编号后再改
            reimburseData.setUsername("jdy-adwdb8lptmsx");
            openPlatformWidgetClient.insert(JSONUtil.toJsonStr(reimburseData));
            ++count[0];
        });


        return count[0];
    }
}
