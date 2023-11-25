package com.aurora.day.auroratimerservernative.schemes.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotEmpty(message = "学号不能为空")
    @Length(max = 13,message = "我觉得你的学号应该没有13位吧?")
    private String id;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
