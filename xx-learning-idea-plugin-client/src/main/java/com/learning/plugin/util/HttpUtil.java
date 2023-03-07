package com.learning.plugin.util;

import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class HttpUtil{

    public static String post(String url, String jsonContent){
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpEntity httpEntity = EntityBuilder.create().setText(jsonContent).build();

            ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url)
                    .setEntity(httpEntity)
                    .build();
            return httpclient.execute(httpPost, response -> {
                final HttpEntity entity= response.getEntity();
                String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);
                return result;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String url){
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(url).build();

            return httpclient.execute(httpGet, response -> {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);
                return result;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String result = get("http://127.0.0.1:8081/learning/question/test");
        System.out.println(result);
    }

}
