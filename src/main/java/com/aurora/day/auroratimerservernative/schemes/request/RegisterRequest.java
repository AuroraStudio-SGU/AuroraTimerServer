package com.aurora.day.auroratimerservernative.schemes.request;

import cn.hutool.crypto.SecureUtil;
import com.aurora.day.auroratimerservernative.pojo.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterRequest {

    @NotEmpty(message = "学号不能为空")
    @Length(max = 13,message = "我觉得你的学号应该没有13位吧?")
    private String id;
    @NotEmpty(message = "姓名不能为空")
    @Length(min = 1,max = 32,message = "你的名字有32个字符???")
    private String name;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 1,max = 32,message = "密码太长拉!")
    private String password;
    @NotEmpty(message = "年级不能为空")
    @Length(max = 3,message = "年级不应该有那么长把?")
    private String grade;

    public User toUser(){
        User temp = new User(id, name, SecureUtil.md5(password));
        temp.setGrade(grade);
        return temp;
    }
}
