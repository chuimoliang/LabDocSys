package com.moliang.labdocsys.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhang qing
 * @date 2022/3/13 14:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userId;

    private Integer role;

}
