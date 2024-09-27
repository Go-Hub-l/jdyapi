package com.xlb.util;

import cn.hutool.json.JSONUtil;
import com.xlb.api.AbstractApiClient;
import com.xlb.conststr.APIURLConst;
import com.xlb.entity.employee.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueryEmployeeUtil extends AbstractApiClient {

    public EmployeeResponse queryEmployeeID(String employeeName) {
        String responseJson = sendPostRequestAndGetResponse(APIURLConst.EMPLOYEE_INFO, employeeName);
        EmployeeResponse employeeResponse = JSONUtil.toBean(responseJson, EmployeeResponse.class);
        return employeeResponse;
    }

    @Override
    protected String getRequestData(String obj) {
        Map<String, String> map = new HashMap<>();
        map.put("username", obj);
        return JSONUtil.toJsonStr(map);
    }
}
