package com.emmett.auto_check.utils;

import cn.hutool.core.util.ObjectUtil;
import com.emmett.auto_check.domain.MyCookieJar;
import com.emmett.auto_check.enums.MediaTypeEnum;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Emmett
 * @description http请求
 * @date Created in 2023/04/10
 */
@Slf4j
public class HttpUtil {
    private static final OkHttpClient client = getHttpClient();
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
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * form 表单提交 发送请求
     * @param requestUrl
     * @param obj
     * @return
     */
    public static Response jsonToFormBodyPost(String requestUrl, Object obj, String efSecurityToken) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("content-type", MediaTypeEnum.APPLICATION_FORM_URLENCODED_VALUE.getMediaType())
                .addHeader("efSecurityToken", efSecurityToken)
                .post(convertObjectToFormBody(obj))
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

    public static Response jsonBodyPost(String requestUrl, Object params, String efSecurityToken) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(RequestBody.create(MediaType.get(MediaTypeEnum.APPLICATION_JSON.getMediaType()), new Gson().toJson(params)))
                .addHeader("efSecurityToken", efSecurityToken)
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
                .get()
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OkHttpClient getHttpClient() {
        CookieJar cookieJar = new MyCookieJar();

        return new OkHttpClient.Builder()
                .readTimeout(6000, TimeUnit.SECONDS)
                .writeTimeout(6000, TimeUnit.SECONDS)
                .connectTimeout(6000, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();
    }

    public static RequestBody convertObjectToFormBody(Object obj) {
        if (obj == null) {
            return null;
        }

        FormBody.Builder formBuilder = new FormBody.Builder();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {
                continue;
            }

            if (fieldValue == null) {
                continue;
            }

            String fieldValueStr = fieldValue.toString();
            formBuilder.addEncoded(fieldName, fieldValueStr);
        }

        return formBuilder.build();
    }
}
