package com.moliang.labdocsys.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

/**
 * @author zhang qing
 * @date 2022/3/13 10:26
 */
public class WebUtil {

    public static String getUserId(HttpServletRequest req) {
        return (String) req.getAttribute("userId");
    }

    public static void setUserId(HttpServletRequest req, String userId) {
        req.setAttribute("userId", userId);
    }

    public static String getRole(HttpServletRequest req) {
        return (String) req.getAttribute("role");
    }

    public static void setRole(HttpServletRequest req, String userId) {
        req.setAttribute("role", userId);
    }

    public static String getCookieFromRequest(HttpServletRequest request) {
        request.getHeader("Authorization");
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return null;
        StringJoiner cookieHeader = new StringJoiner("; ");
        for (Cookie cookie : cookies) {
            cookieHeader.add(cookie.getName() + "=" + cookie.getValue());
        }
        return cookieHeader.toString();
    }

}
