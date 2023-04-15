package com.emmett.auto_check.domain;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.List;

public class MyCookieJar implements CookieJar {

    private final List<Cookie> cookies = new ArrayList<>();

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookies.addAll(cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> result = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (cookie.matches(url)) {
                result.add(cookie);
            }
        }
        return result;
    }
}