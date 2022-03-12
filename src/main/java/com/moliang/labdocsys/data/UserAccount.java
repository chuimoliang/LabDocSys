package com.moliang.labdocsys.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.statement.alter.ValidateConstraint;

import java.util.Date;

/**
 * @author zhang qing
 * @date 2022/3/12 22:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "user_account")
public class UserAccount {

    /**
     * 账号 id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 用户密码
     */
    @TableField(value = "user_password")
    private String userPassword;

    /**
     * 用户角色
     */
    @TableField(value = "user_role")
    private int userRole;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

}
