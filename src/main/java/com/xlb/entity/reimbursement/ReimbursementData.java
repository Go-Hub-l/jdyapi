package com.xlb.entity.reimbursement;

import lombok.Data;

import java.util.Map;

@Data
public class ReimbursementData {
    /**
     * 值类型
     * “报销金额_*”: {”1“： 300， “2“： 500, “3“： 700}
     * ...
     */
    private Map<String, Object> dataDetails;
}
