package com.aurora.day.auroratimerserver.schemes.eums;


import lombok.Getter;


@Getter
public enum ResponseState {
    NORMAL(1, 200, "操作成功"),
    ERROR(2, 100, "后端崩咯"),
    IllegalArgument(3, 1000, "参数错了"),
    DateBaseError(4, 1001, "数据库崩了"),
    AuthorizationError(5,403,"token验证失败或权限不足,尝试重新登录把");
    private final int index;
    private final int code;
    private String msg;

    ResponseState(int index, int code, String msg) {
        this.index = index;
        this.code = code;
        this.msg = msg;
    }

    public void setMsg(String value) {
        this.msg = value;
    }

    public String appendGet(String appendMsg) {
        this.msg = this.msg + ":" + appendMsg;
        return this.msg;
    }
}
