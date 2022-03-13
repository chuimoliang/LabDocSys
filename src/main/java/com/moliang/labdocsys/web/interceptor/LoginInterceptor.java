package com.moliang.labdocsys.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.moliang.labdocsys.config.Role;
import com.moliang.labdocsys.data.User;
import com.moliang.labdocsys.util.JwtTokenUtil;
import com.moliang.labdocsys.util.WebUtil;
import com.moliang.labdocsys.web.common.WebRespCode;
import com.moliang.labdocsys.web.common.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author zhang qing
 * @date 2022/3/13 12:36
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final Pattern IGNORE_LOGIN_URL = Pattern.compile("/api/user/login");

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (IGNORE_LOGIN_URL.matcher(req.getRequestURI()).find()) {
            return true;
        }
        String token = req.getHeader("Authorization");
        if (token == null || JwtTokenUtil.isTokenExpired(token)) {
            refuseRequest(resp);
            return false;
        }
        User user = JwtTokenUtil.getUserFromToken(token);
        if (user == null || user.getRole() == null) {
            refuseRequest(resp);
            return false;
        }
        Role role = Role.getRoleByCode(user.getRole());
        if (role != null) {
            WebUtil.setUserId(req, user.getUserId());
            WebUtil.setRole(req, role.getRoleName());
            return true;
        }
        refuseRequest(resp);
        return false;
    }

    private void refuseRequest(HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONObject.toJSON(WebResponse.fail(WebRespCode.NOT_LOGIN)));
        response.getWriter().flush();
    }
}