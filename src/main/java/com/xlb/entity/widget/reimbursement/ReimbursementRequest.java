package com.xlb.entity.widget.reimbursement;

import lombok.Data;

import java.util.Map;

@Data
public class ReimbursementRequest {
    private String app_id;
    private String entry_id;
    private String creator;
    private Map<String, Object> data;
}
