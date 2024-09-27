package com.xlb.entity;

import lombok.Data;

import java.util.List;

@Data
public class WidgetResponse {
    private List<Widget> forms;
    @Data
    public static class Widget {
        private String name;
        private String app_id;
        private String entry_id;
    }
}
