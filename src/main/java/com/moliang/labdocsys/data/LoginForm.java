package com.moliang.labdocsys.data;

import com.moliang.labdocsys.aop.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhang qing
 * @date 2022/3/15 16:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    private String userId;

    private String password;

}
