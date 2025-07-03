package com.aurora.day.auroratimerserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T obj) {
        return new Result<>(200,"ok", obj);
    }

    public static Result fail(String message) {
        return new Result(405,message, null);
    }

    public static Result<String> success(boolean bol) {
        return bol ?
                new Result<>(200,"操作成功", "") :
                new Result<>(500,"操作失败", "");
    }
}
