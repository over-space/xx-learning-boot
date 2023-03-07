package com.learning.plugin.util;

public final class HttpUtil{

    public static String post(){
        //构建HttpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        //指定POST请求
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);

        //包装请求体
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.addAll(content);
        HttpEntity request = new UrlEncodedFormEntity(params, "UTF-8");

        //发送请求
        httppost.setEntity(request);
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);

        //读取响应
        HttpEntity entity = httpResponse.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "UTF-8");
        }
    }

}
