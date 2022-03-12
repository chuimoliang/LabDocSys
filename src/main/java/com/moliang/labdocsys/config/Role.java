package com.moliang.labdocsys.config;

/**
 * @author zhang qing
 * @date 2022/3/12 16:39
 */
public enum Role {

    ADMIN(0, "管理员"),
    TEACHER(1, "老师"),
    STUDENT(2, "学生");

    private int role;
    private String roleName;

    public static Role getRoleByCode(int code) {
        switch (code) {
            case 0 : return ADMIN;
            case 1 : return TEACHER;
            case 2 : return STUDENT;
        }
        return null;
    }

    Role(int role, String roleName) {
        this.role = role;
        this.roleName = roleName;
    }

    public int getRole() {
        return role;
    }

    public String getRoleName() {
        return roleName;
    }
}
