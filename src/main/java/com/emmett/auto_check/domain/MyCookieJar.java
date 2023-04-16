package com.emmett.auto_check.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class MyCookieJar implements CookieJar {

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private static String fixCookieValue;

    private static Cookie fixCookie;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.name().equals("iplat.sessionId")) {
                fixCookieValue = cookie.value();
                fixCookie = cookie;
            }
        }
        String host = url.host();
        if (ObjectUtil.isNotEmpty(cookieStore)) {
            cookieStore.remove(host);
        }
        List<Cookie> cookiesCopy = new ArrayList<>(cookies);
        cookiesCopy.add(fixCookie);
        cookieStore.put(host, cookiesCopy);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String host = url.host();
        List<Cookie> cookies = cookieStore.get(host);
        return cookies != null ? cookies : new ArrayList<>();
    }

    public static String getFixCookieValue() {
        return fixCookieValue;
    }
}