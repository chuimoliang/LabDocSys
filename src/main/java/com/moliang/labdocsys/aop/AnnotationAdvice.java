package com.moliang.labdocsys.aop;

import com.moliang.labdocsys.config.Role;
import com.moliang.labdocsys.util.WebUtil;
import com.moliang.labdocsys.web.common.WebRespCode;
import com.moliang.labdocsys.web.common.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author zhang qing
 * @date 2022/3/13 14:37
 */
@Aspect
@Slf4j
@Component
public class AnnotationAdvice {

    @Around("@annotation(admin)")
    public Object adminAdvice(ProceedingJoinPoint thisJoinPoint, Admin admin) throws Throwable {
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (Role.ADMIN.getRoleName().equals(WebUtil.getRole(request))) {
            return thisJoinPoint.proceed();
        }
        log.error("权限不足访问管理员接口, userId:[{}], role:[{}]", WebUtil.getUserId(request), WebUtil.getRole(request));
        return WebResponse.fail(WebRespCode.NOT_LOGIN);
    }

    @Around("@annotation(teacher)")
    public Object adminAdvice(ProceedingJoinPoint thisJoinPoint, Teacher teacher) throws Throwable {
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (Role.TEACHER.getRoleName().equals(WebUtil.getRole(request))) {
            return thisJoinPoint.proceed();
        }
        log.error("权限不足访问教师接口, userId:[{}], role:[{}]", WebUtil.getUserId(request), WebUtil.getRole(request));
        return WebResponse.fail(WebRespCode.NOT_LOGIN);
    }

}
