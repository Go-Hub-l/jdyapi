package com.xlb.api;

import com.xlb.conststr.APIURLConst;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractApiClient {

    protected String getCurrentApplicationID() { return "";};
    protected String getCurrentEntryID() { return "";};
    protected String getRequestData(String obj){ return "";};

    /**
     * 发送POST请求并获取响应数据
     * @param urlStr
     * @return
     */
    public String sendPostRequestAndGetResponse(String urlStr, String obj) {
        try {
            //组装请求数据
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + APIURLConst.API_KEY);

            conn.setDoOutput(true);

            conn.connect();
            try(OutputStream os = conn.getOutputStream()) {
                String requestData = getRequestData(obj);
                os.write(requestData.getBytes("UTF-8"));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String response = sb.toString();
                br.close();
                return response;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "";
    }
}
