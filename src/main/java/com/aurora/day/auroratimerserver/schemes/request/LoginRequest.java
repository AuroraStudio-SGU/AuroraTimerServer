package com.aurora.day.auroratimerserver.schemes.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotEmpty(message = "学号不能为空")
    @Length(max = 13,message = "我觉得你的学号应该没有13位吧?")
    @Schema(name = "id",description = "学号(长度12位)",example = "21125023000")
    private String id;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 1,max = 32,message = "密码太长拉!")
    @Schema(name = "password",description = "密码")
    private String password;
}
