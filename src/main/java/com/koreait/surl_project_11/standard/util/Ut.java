package com.koreait.surl_project_11.standard.util;

import com.koreait.surl_project_11.global.app.AppConfig;
import lombok.SneakyThrows;

public class Ut {
    public static class str {
        public static boolean isBlank(String str) {
            return str == null || str.trim().isBlank();
        }

        // public static boolean isNotBlank(String str) {}
        public static boolean hasLength(String str) {
            return !isBlank(str);
        }
    }

    public static class json {
        // 예외상황 있으면 알아서 처리해 -> 권장하진 않는다.(lombok 공식사이트에서도 권장하지 않는다고 함)
        @SneakyThrows
        public static String toString(Object obj) {
            return AppConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
    }
}
