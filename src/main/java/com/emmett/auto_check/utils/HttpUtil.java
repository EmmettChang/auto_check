package com.emmett.auto_check.utils;

import cn.hutool.core.util.ObjectUtil;
import com.emmett.auto_check.enums.MediaTypeEnum;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Emmett
 * @description http请求
 * @date Created in 2023/04/10
 */
public class HttpUtil {

    /**
     * form 表单提交 发送请求
     * @param requestUrl
     * @param params
     * @return
     */
    public static Response formBodyPost(String requestUrl, HashMap<String,String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (ObjectUtil.isNotEmpty(params)) {
            for (String key : params.keySet()) {
                if (ObjectUtil.isNotEmpty(params.get(key))) {
                    builder.add(key, params.get(key));
                }
            }
        }
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("content-type", MediaTypeEnum.APPLICATION_FORM_URLENCODED_VALUE.getMediaType())
                .post(builder.build())
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(6000, TimeUnit.SECONDS)
                .writeTimeout(6000, TimeUnit.SECONDS)
                .connectTimeout(6000, TimeUnit.SECONDS)
                .build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * form 表单提交 发送请求
     * @param requestUrl
     * @param params
     * @return
     */
    public static Response jsonBodyPost(String requestUrl, Object params) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(RequestBody.create(MediaType.get(MediaTypeEnum.APPLICATION_JSON.getMediaType()), new Gson().toJson(params)))
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(6000, TimeUnit.SECONDS)
                .writeTimeout(6000, TimeUnit.SECONDS)
                .connectTimeout(6000, TimeUnit.SECONDS)
                .build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * form get提交 发送请求
     * @param requestUrl
     * @return
     */
    public static Response jsonGet(String requestUrl) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("content-type", MediaTypeEnum.APPLICATION_JSON.getMediaType())
                .get()
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(6000, TimeUnit.SECONDS)
                .writeTimeout(6000, TimeUnit.SECONDS)
                .connectTimeout(6000, TimeUnit.SECONDS)
                .build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
