package com.aurora.day.auroratimerserver.schame.req;

import lombok.Data;
import org.noear.solon.validation.annotation.Length;
import org.noear.solon.validation.annotation.NotEmpty;

@Data
public class LoginReq {
    //学号
    @NotEmpty(message = "学号不能为空")
    @Length(max = 13,message = "我觉得你的学号应该没有13位吧?")
    private String id;
    //密码
    @NotEmpty(message = "密码不能为空")
    @Length(min = 1,max = 32,message = "密码太长拉!")
    private String password;
}
