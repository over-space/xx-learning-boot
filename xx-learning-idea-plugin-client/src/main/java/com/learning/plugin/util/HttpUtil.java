package com.learning.plugin.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.learning.plugin.vo.QuestionVO;
import com.learning.plugin.vo.ResponseResult;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class HttpUtil {


    public static <T> ResponseResult<T> post(String url, String jsonContent, Class<T> targetClazz) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpEntity httpEntity = EntityBuilder.create().setText(jsonContent).build();

            ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url)
                    .setEntity(httpEntity)
                    .build();
            return httpclient.execute(httpPost, response -> {
                final HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);
                return handle(result, targetClazz);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ResponseResult<T> get(String url, Class<T> targetClazz) {
        return handle(get(url), targetClazz);
    }

    public static String get(String url) {
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

    private static <T> ResponseResult<T> handle(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, new TypeReference<ResponseResult<JSONObject>>(clazz) {});
    }

    public static void main(String[] args) {
        ResponseResult<QuestionVO> result = get("http://127.0.0.1:8081/question/random", QuestionVO.class);

        System.out.println(result);

    }

}
