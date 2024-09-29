package com.xlb.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.xlb.api.AbstractApiClient;
import com.xlb.conststr.APIURLConst;
import com.xlb.entity.employee.EmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EmployeeUtil extends AbstractApiClient {
    private RequestType requestType;
    private enum RequestType {
        QUERY_EMPLOYEE_ID,
        CREATE_EMPLOYEE
    }

    public EmployeeResponse queryEmployeeID(String employeeName) {
        requestType = RequestType.QUERY_EMPLOYEE_ID;

        String responseJson = sendPostRequestAndGetResponse(APIURLConst.EMPLOYEE_INFO, employeeName);
        Assert.notBlank(responseJson, "responseJson is blank");

        EmployeeResponse employeeResponse = JSONUtil.toBean(responseJson, EmployeeResponse.class);
        Assert.notNull(employeeResponse, "employeeResponse is null");

        if (employeeResponse.getCode() != 0) {
            log.info("query employee failed, code:{} message:{}", employeeResponse.getCode(), employeeResponse.getMsg());
            return null;
        }

        return employeeResponse;
    }

    public EmployeeResponse createEmployee(String employeeInfo) {
        requestType = RequestType.CREATE_EMPLOYEE;

        String responseJson = sendPostRequestAndGetResponse(APIURLConst.EMPLOYEE_CREATE, employeeInfo);
        Assert.notBlank(responseJson, "responseJson is blank");

        EmployeeResponse employeeResponse = JSONUtil.toBean(responseJson, EmployeeResponse.class);
        Assert.notNull(employeeResponse, "employeeResponse is null");

        if (employeeResponse.getCode() != 0) {
            log.info("create employee failed, code:{} message:{}", employeeResponse.getCode(), employeeResponse.getMsg());
            return null;
        }

        return employeeResponse;
    }

    @Override
    protected String getRequestData(String obj) {
        switch (requestType) {
            case QUERY_EMPLOYEE_ID:
            {
                Map<String, String> map = new HashMap<>();
                map.put("username", obj);

                return JSONUtil.toJsonStr(map);
            }
            case CREATE_EMPLOYEE:
            {
                return obj;
            }
            default:
                break;
        }

        return "";
    }
}
