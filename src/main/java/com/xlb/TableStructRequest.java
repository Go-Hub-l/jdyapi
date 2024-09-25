package com.xlb;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class TableStructRequest {
    private String app_id;
    private String entry_id;
    private Map<String, Object> data;

//    @lombok.Data
//    public static class Data {
        //报销人
//        private String _widget_1727145447534;
//
//        //费用日期
//        private String _widget_1727145447535;
//
//        //报销金额
//        private BigDecimal _widget_1727145447537;
//
//        //说明
//        private String _widget_1727145447536;

//        public void setBXPeople(String people) {
//            this._widget_1727145447534 = people;
//        }
//        public void setBXDate(String date) {
//            this._widget_1727145447535 = date;
//        }
//        public void setBXAmount(BigDecimal amount) {
//            this._widget_1727145447537 = amount;
//        }
//
//        public void setBXDesc(String desc) {
//            this._widget_1727145447536 = desc;
//        }
//
//        public String getBXPeople() {
//            return this._widget_1727145447534;
//        }
//        public String getBXDate() {
//            return this._widget_1727145447535;
//        }
//        public BigDecimal getBXAmount() {
//            return this._widget_1727145447537;
//        }
//        public String getBXDesc() {
//            return this._widget_1727145447536;
//        }
//    }
}
