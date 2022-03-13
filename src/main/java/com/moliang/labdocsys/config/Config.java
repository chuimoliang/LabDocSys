package com.moliang.labdocsys.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhang qing
 * @date 2022/3/12 15:02
 */
@Data
@Component
public class Config {

    private static String secret = "moliang";

    private static Long expiration = 86400L;

    private static String tokenHead = "Authorization";

    public static String getSecret() {
        return secret;
    }

    public static Long getExpiration() {
        return expiration;
    }

    public static String getTokenHead() {
        return tokenHead;
    }

    @Value("${live.jdbc.user}")
    private String liveUser;

}
