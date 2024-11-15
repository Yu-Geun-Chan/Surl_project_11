package com.koreait.surl_project_11.standard.util;

public class Ut {
    public static class str {
        public static boolean isBlank (String str) {
            return str == null || str.trim().isBlank();
        }
        // public static boolean isNotBlank(String str) {}
        public static boolean hasLength (String str) {
            return !isBlank(str);
        }
    }
}
