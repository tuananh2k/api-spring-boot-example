package com.example.helloword.utils;

public class StringUtils {
    public StringUtils() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
