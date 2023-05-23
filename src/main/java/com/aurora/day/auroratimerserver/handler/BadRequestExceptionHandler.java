package com.aurora.day.auroratimerserver.handler;

import cn.hutool.json.JSONObject;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.R_MsgList;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class BadRequestExceptionHandler {
    /**
     * 校验错误拦截处理
     *
     * @param exceptions 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(BindException.class)
    public R_MsgList validationBodyException(BindingResult exceptions) {
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            List<String> errorMsgList = new ArrayList<>(errors.size());
            if (!errors.isEmpty()) {
                errors.forEach(objectError -> {
                    errorMsgList.add(objectError.getDefaultMessage());
                });
                return R.errorList(errorMsgList);
            }
        }
        return R_MsgList.error("请求参数错误");
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R RequestMethodException(HttpRequestMethodNotSupportedException e) {
        return R.error("请求方式错误:" + e.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public R GlobalException(Exception e) {
        JSONObject error = new JSONObject();
        error.set("reason", e.getLocalizedMessage());
        error.set("stacks", e.getStackTrace());
        return R.error("未分类错误", error);
    }


}