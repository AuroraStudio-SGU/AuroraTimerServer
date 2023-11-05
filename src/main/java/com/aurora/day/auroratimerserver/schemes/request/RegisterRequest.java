package com.aurora.day.auroratimerserver.schemes.request;

import com.aurora.day.auroratimerserver.pojo.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterRequest {

    @NotEmpty(message = "学号不能为空")
    @Length(max = 13,message = "我觉得你的学号应该没有13位吧?")
    @Schema(name = "id",description = "学号(长度12位)",example = "21125023000")
    private String id;
    @NotEmpty(message = "姓名不能为空")
    @Length(min = 1,max = 32,message = "你的名字有32个字符???")
    @Schema(name = "name",description = "密码",example = "114514")
    private String name;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 1,max = 32,message = "密码太长拉!")
    @Schema(name = "password",description = "密码")
    private String password;
    @NotEmpty(message = "年级不能为空")
    @Length(max = 3,message = "年级不应该有那么长把?")
    @Schema(name = "grade",description = "年级(长度3位)",example = "21级")
    private String grade;

    public User toUser(){
        User temp = new User(id, name, password);
        temp.setGrade(grade);
        return temp;
    }
}
