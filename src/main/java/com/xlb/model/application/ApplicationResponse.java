package com.xlb.model.application;


import lombok.Data;

import java.util.List;

@Data
public class ApplicationResponse {
    private List<Application> apps;
    @Data
    public static class Application {
        private String app_id;
        private String name;
    }
}
