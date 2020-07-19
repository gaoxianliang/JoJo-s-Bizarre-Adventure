package com.phantomblood.jojo.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;


public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(com.patrol.utils.HttpUtil.class);


    /**
     * pose方式请求
     *
     * @param url
     * @return {statusCode : "请求结果状态代码", responseString : "请求结果响应字符串"}
     */
    public static Map post(String url, Map<String, String> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost post = postForm(url, params);

        Map reponseMap = invoke(httpclient, post);
        httpclient.getConnectionManager().shutdown();

        return reponseMap;
    }

    /**
     * get方式请求
     *
     * @param url
     * @return {statusCode : "请求结果状态代码", responseString : "请求结果响应字符串"}
     */
    public static Map get(String url) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        Map reponseMap = invoke(httpclient, get);
        httpclient.getConnectionManager().shutdown();
        return reponseMap;
    }

    public static Map postJson(String url, String json) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        StringEntity s;
        Map reponseMap = null;
        s = new StringEntity(json, "utf-8");
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json; charset=utf-8");
        HttpPost post = new HttpPost(url);
        reponseMap = invoke(httpclient, post);
        httpclient.getConnectionManager().shutdown();
        return reponseMap;

    }


    private static HttpPost postForm(String url, Map<String, String> params) {

        HttpPost httpost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }


    private static Map invoke(DefaultHttpClient httpclient,
                              HttpUriRequest httpost) {

        Map returnMap = new HashMap();

        HttpResponse response = sendRequest(httpclient, httpost);
//	    System.out.println("return code:"+response.getStatusLine().getStatusCode());
        String body = paseResponse(response);
//		System.out.println(body);

        returnMap.put("statusCode", response.getStatusLine().getStatusCode());    // 请求返回结果状态
        returnMap.put("response", body);
        return returnMap;
    }


    private static HttpResponse sendRequest(DefaultHttpClient httpclient,
                                            HttpUriRequest httpost) {

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static String paseResponse(HttpResponse response) {

        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);


        String body = null;
        try {
            body = EntityUtils.toString(entity, "UTF-8");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    /**
     * 入参说明
     * <p>
     * param url 请求地址
     * param jsonObject	请求的json数据
     * param encoding	编码格式
     */
    public static String jsonPost(String url, String json) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String response = null;
        try {
            StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                response = EntityUtils.toString(res.getEntity());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static String sendJsonPost(String url, String body) {
//        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setConfig(RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(5000).build());
            post.addHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(body));
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("HttpClient error, url:{}, body:{}", url, body);
            return "";
        }
    }
    public static String sendXmlPost(String url, String body) {
//        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setConfig(RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(5000).build());
            post.addHeader("Content-Type", "application/xml");
            post.setEntity(new StringEntity(body));
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String s = response.getEntity().toString();
                return EntityUtils.toString(response.getEntity(), "gbk");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("HttpClient error, url:{}, body:{}", url, body);
            return "";
        }
    }

    public static String sendJsonPost(String url, Object target) {
        return sendJsonPost(url, JSON.toJSONString(target));
    }

}
