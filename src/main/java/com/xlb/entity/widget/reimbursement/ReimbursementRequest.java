package com.xlb.entity.widget.reimbursement;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReimbursementRequest {
    private String app_id;
    private String entry_id;
    private String data_creator;
    private String creator;
    private List<Map<String, Object>> data_list;
}
