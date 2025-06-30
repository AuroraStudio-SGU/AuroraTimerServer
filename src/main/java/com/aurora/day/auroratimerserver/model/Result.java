package com.aurora.day.auroratimerserver.model;

import lombok.Data;

@Data
public class Result<T> {
    private String message;
    private T data;

    public static <T> Result<T> success(T obj) {
        return new Result<>("ok", obj);
    }

    public static Result fail(String message) {
        return new Result(message, null);
    }

    private Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static Result<String> success(boolean bol) {
        return bol ?
                new Result<>("操作成功", "") :
                new Result<>("操作失败", "");
    }
}
