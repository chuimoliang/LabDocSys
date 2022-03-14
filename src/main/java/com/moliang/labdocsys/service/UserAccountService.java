package com.moliang.labdocsys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moliang.labdocsys.aop.Admin;
import com.moliang.labdocsys.data.UserAccount;
import com.moliang.labdocsys.mapper.UserAccountMapper;
import com.moliang.labdocsys.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    public Object login(String userId, String password) {
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

}
