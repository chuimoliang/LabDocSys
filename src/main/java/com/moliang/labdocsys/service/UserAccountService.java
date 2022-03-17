package com.moliang.labdocsys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moliang.labdocsys.aop.Admin;
import com.moliang.labdocsys.config.Role;
import com.moliang.labdocsys.data.UserAccount;
import com.moliang.labdocsys.mapper.UserAccountMapper;
import com.moliang.labdocsys.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhang qing
 * @date 2022/3/13 17:09
 */
@Component
@Slf4j
public class UserAccountService {

    @Resource
    private UserAccountMapper userAccountMapper;

    /**
     * 用户登录, 返回token
     *
     * 时间限制省略掉在服务端进行密码加密的过程
     */
    public String login(String userId, String password) {
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", userId);
        UserAccount user = userAccountMapper.selectOne(queryWrapper);
        if (user == null || !password.equals(user.getUserPassword())) {
            return null;
        }
        return JwtTokenUtil.generateToken(userId, user.getUserRole());
    }

    /**
     * 生成用户
     */
    public int generateUser(String userId, Integer role) {
        UserAccount account = UserAccount.builder().account(userId)
                .userPassword("123456")
                .userRole(role)
                .build();
        return userAccountMapper.insert(account);
    }

    /**
     * 返回用户菜单
     * pms 实验
     * - product 实验列表
     * - addProduct 创建实验
     * - updateProduct 更新实验
     * oms 报告
     * - order 报告列表
     * - orderDetail 提交报告
     * - markOrder 批阅报告
     * ums 账户
     * - admin 账户列表
     * - addUser 创建用户
     * - updateUser 更新用户
     * - self 个人信息
     */
    public List<String> getMenus(String role) {
        if (Role.ADMIN.getRoleName().equals(role)) {
            return Arrays.asList("pms", "product", "addProduct", "updateProduct", "oms", "order"
            , "orderDetail", "markOrder", "ums", "admin", "updateUser");
        }
        if (Role.TEACHER.getRoleName().equals(role)) {
            return Arrays.asList("pms", "product", "addProduct", "updateProduct", "oms", "order"
                    , "markOrder", "ums", "self");
        }
        if (Role.STUDENT.getRoleName().equals(role)) {
            return Arrays.asList("pms", "product", "oms", "order", "orderDetail", "ums", "self");
        }
        return null;
    }

}
