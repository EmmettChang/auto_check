package com.emmett.auto_check.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class MyCookieJar implements CookieJar {

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String host = url.host();
        if (ObjectUtil.isNotEmpty(cookieStore)) {
            cookieStore.remove(host);
        }
        cookieStore.put(host, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String host = url.host();
        List<Cookie> cookies = cookieStore.get(host);
        return cookies != null ? cookies : new ArrayList<>();
    }
}