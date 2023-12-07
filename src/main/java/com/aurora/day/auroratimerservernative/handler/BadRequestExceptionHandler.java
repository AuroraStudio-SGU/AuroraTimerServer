package com.aurora.day.auroratimerservernative.handler;

import com.aurora.day.auroratimerservernative.exceptions.UserServicesException;
import com.aurora.day.auroratimerservernative.schemes.R;
import com.aurora.day.auroratimerservernative.schemes.R_MsgList;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
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
                return R.errorList(ResponseState.IllegalArgument,errorMsgList);
            }
        }
        return R_MsgList.error(ResponseState.IllegalArgument.replaceMsg("请求参数错误"));
    }

    @ExceptionHandler(UserServicesException.class)
    public R UserServiceException(UserServicesException e){
        return R.error(e.getState(),e,false);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R RequestMethodException(HttpRequestMethodNotSupportedException e) {
        return R.error(ResponseState.IllegalArgument.replaceMsg("请求方式错误"),e,false);
    }

//    @ExceptionHandler(Exception.class)
    public R GlobalException(Exception e) {
        return R.error(ResponseState.ERROR, e,true);
    }


}