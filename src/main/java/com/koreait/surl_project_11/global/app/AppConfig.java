package com.koreait.surl_project_11.global.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Getter
    public static ObjectMapper objectMapper;
    @Getter
    private static String jwtSecretKey;
    @Getter
    private static long accessTokenExpirationSec;

    // jackson의 Bean을 가져온다.
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 보안성이 매우 뛰어나다.
    }

    @Value("${custom.secret.jwt.secretKey}")
    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    @Value("${custom.accessToken.expirationSec}")
    public void setJwtSecretKey(long accessTokenExpirationSec) {
        this.accessTokenExpirationSec = accessTokenExpirationSec;
    }

    @Getter
    private static String siteFrontUrl;
    @Value("${custom.site.frontUrl}")
    public void setSiteFrontUrl(String siteFrontUrl) {
        this.siteFrontUrl = siteFrontUrl;
    }
    @Getter
    private static String siteBackUrl;
    @Value("${custom.site.backUrl}")
    public void setSiteBackUrl(String siteBackUrl) {
        this.siteBackUrl = siteBackUrl;
    }
    @Getter
    private static String siteCookieDomain;
    @Value("${custom.site.cookieDomain}")
    public void setSiteCookieDomain(String siteCookieDomain) {
        this.siteCookieDomain = siteCookieDomain;
    }

}

