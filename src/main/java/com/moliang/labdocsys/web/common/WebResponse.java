package com.moliang.labdocsys.web.common;

import java.io.Serializable;

/**
 * @author zhang qing
 * @date 2022/3/12 23:18
 */
public class WebResponse implements Serializable {
    private final String message;
    private final int code;
    private final Object data;
    private final boolean success;

    private WebResponse(String message, WebRespCode code, Object data, boolean success) {
        this.code = code.getCode();
        this.data = data;
        this.message = message;
        this.success = success;
    }

    private WebResponse(String message, int code, Object data, boolean success) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public static WebResponse success() {
        return new WebResponse("", WebRespCode.SUCCESS, null, true);
    }

    public static WebResponse success(Object data) {
        return new WebResponse("", WebRespCode.SUCCESS, data, true);
    }

    public static WebResponse fail(WebRespCode code) {
        return new WebResponse(code.getMsg(), code, null, false);
    }

    public static WebResponse fail(WebRespCode code, String msg) {
        return new WebResponse(msg, code, null, false);
    }


    public static WebResponse fail(int code, String msg, String data) {
        return new WebResponse(msg, code, data, false);
    }

    public static WebResponse fail(String message) {
        return fail(WebRespCode.FAIL.getCode(), message, null);
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }
}
