package com.xlb.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.xlb.api.openplatform.OpenPlatformWidgetClient;
import com.xlb.conststr.ReimbursementConst;
import com.xlb.entity.employee.EmployeeResponse;
import com.xlb.syncapi.api.ReimbursementSyncService;
import com.xlb.util.EmployeeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.xlb.conststr.CODE;


@Slf4j
@DubboService
@Component
public class ReimbursementSync implements ReimbursementSyncService {
    @Autowired
    private OpenPlatformWidgetClient openPlatformWidgetClient;

    @Autowired
    private EmployeeUtil employeeUtil;

    @Override
    public int insertBatch(String obj) {
        if (!StringUtils.hasLength(obj)) {
            log.info("object is null or empty");
            return 0;
        }
        log.info("insertBatch start, obj: {}", obj);

        try {
            Map reimbursementData = JSONUtil.toBean(obj, Map.class);

            if (reimbursementData == null || reimbursementData.isEmpty()) {
                log.info("insertBatch failed, reimbursementData is null or empty");
                return 0;
            }

            if (!reimbursementData.containsKey("dataDetails") || !reimbursementData.containsKey("user")) {
                log.info("reimbursementData is not valid");
                return 0;
            }

            Map<String, Object> dataDetails = (Map<String, Object>)reimbursementData.get("dataDetails");
            Map<String, String> userInfo = (Map<String, String>)reimbursementData.get("user");

            //先根据用户username查询用户信息，如果没有则创建用户
            EmployeeResponse employeeResponse = employeeUtil.queryEmployeeID(userInfo.get("username"));

            if (employeeResponse == null || employeeResponse.getUser() == null) {
                //新建用户
                employeeResponse = employeeUtil.createEmployee(JSONUtil.toJsonStr(userInfo));
                if (employeeResponse == null || employeeResponse.getCode() != CODE.SUCCESS) {
                    log.info("create employee failed, username:{}", userInfo.get("username"));
                    return 0;
                }

            }

            Assert.notEmpty(reimbursementData, "reimbursementData is null");

            Map<String, String> bxje = (Map<String, String>)dataDetails.get(ReimbursementConst.BXJE);
            Map<String, String> sm = (Map<String, String>)dataDetails.get(ReimbursementConst.SM);
            Map<String, String> fylx = (Map<String, String>)dataDetails.get(ReimbursementConst.FYLX);
            Map<String, String> fyrq = (Map<String, String>)dataDetails.get(ReimbursementConst.FYRQ);
            String bjr = (String)dataDetails.get(ReimbursementConst.BXR);

            if (bxje == null || fylx == null || fyrq == null || bjr == null) {
                log.info("insert data is not valid, bxje: {}, fylx: {}, fyrq: {}, bjr: {}", bxje, fylx, fyrq, bjr);
                return 0;
            }
            List<OpenPlatformWidgetClient.ReimburseData> reimburseDataList = new ArrayList<>();

            //转换数据
            bxje.forEach((k, v) -> {
                OpenPlatformWidgetClient.ReimburseData reimburseData = new OpenPlatformWidgetClient.ReimburseData();
                reimburseData.setAmount(v);
                reimburseData.setDate(fyrq.getOrDefault(k, ""));
                reimburseData.setDesc(String.format("%s_%s", fylx.getOrDefault(k, "类型"), sm.getOrDefault(k, "无")));
                //根据姓名获取用户编号
                reimburseData.setUsername(userInfo.get("username"));

                reimburseDataList.add(reimburseData);
            });
            openPlatformWidgetClient.insertBatch(JSONUtil.toJsonStr(reimburseDataList));
            return reimburseDataList.size();
        } catch (Exception e) {
            log.error("insertBatch failed, obj: {}, error: {}", obj, e);
        }

        return 0;
    }
}
