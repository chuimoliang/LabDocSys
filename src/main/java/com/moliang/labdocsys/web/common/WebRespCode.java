package com.moliang.labdocsys.web.common;

/**
 * @author zhang qing
 * @date 2022/3/12 23:08
 */
public enum WebRespCode {

    SUCCESS(200, "响应成功"),
    PARAM_ERROR(10400, "参数错误，非法请求"),
    NOTFOUND(404, "请求数据不存在"),
    NOT_LOGIN(401, "未登录或权限不足"),
    BAD_REQUEST(500, "服务器内部错误"),
    FAIL(0, "请求失败");

    private final int code;

    private final String msg;

    WebRespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public WebRespCode getRespCode() {
        return this;
    }
}
