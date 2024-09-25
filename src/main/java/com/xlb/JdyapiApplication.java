package com.xlb;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

@SpringBootApplication
public class JdyapiApplication {

    public static void main(String[] args) {
//        SpringApplication.run(JdyapiApplication.class, args);
        String apiKey = "ZR3Tbm1ggddw37b7YlBmkjZ4AubJLkmr";
//        String urlStr = "https://api.jiandaoyun.com/api/v5/app/list";
        String appid = "66f225e3257a46efaacce605";
        String entry_id = "66f225e7ba2ab32ebbb52966";
//        String urlStr = "https://api.jiandaoyun.com/api/v5/app/entry/list";
//        String urlStr = "https://api.jiandaoyun.com/api/v5/app/entry/widget/list";
//        String urlStr = "https://api.jiandaoyun.com/api/v5/app/entry/data/list";
        String urlStr = "https://api.jiandaoyun.com/api/v5/app/entry/data/create";
        try {
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);
            conn.connect();

//            FormRequest form = new FormRequest();
//            form.setApp_id(appid);
//            form.setEntry_id(entry_id);
            TableStructRequest form = new TableStructRequest();
            form.setApp_id(appid);
            form.setEntry_id(entry_id);

            form.setData(new HashMap<>());
            form.getData().put("_widget_1727145447534", new HashMap<>(){{put("value", "R-06bw8Pn2");}});
            form.getData().put("_widget_1727145447535", new HashMap<>(){{put("value", "2024-09-25");}});
            form.getData().put("_widget_1727145447537", new HashMap<>(){{put("value", "333");}});
            form.getData().put("_widget_1727145447536", new HashMap<>(){{put("value", "测试数据");}});


            try(OutputStream os = conn.getOutputStream()) {
                String requestJson = JSONUtil.toJsonStr(form);
                os.write(requestJson.getBytes("UTF-8"));
                os.flush();
            }

            StringBuilder sb = new StringBuilder();
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            String res = sb.toString();
            System.out.println(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
