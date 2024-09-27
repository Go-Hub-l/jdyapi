package com.xlb.entity.employee;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponse {
    private UserInfo user;

    @Data
    public static class UserInfo {
        private String username;
        private String name;
        private List<Integer> departments;
        private Integer type;
        private Integer status;
        private String integrate_id;
    }
}
