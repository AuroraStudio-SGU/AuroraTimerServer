package com.aurora.day.auroratimerserver.schame.req;

import com.aurora.day.auroratimerserver.model.User;
import lombok.Data;
import org.noear.solon.validation.annotation.Length;
import org.noear.solon.validation.annotation.NotEmpty;

@Data
public class RegistryUserReq {
    //学号
    @NotEmpty(message = "学号不能为空")
    @Length(max = 13,message = "我觉得你的学号应该没有13位吧?")
    private String id;
    //姓名
    @NotEmpty(message = "姓名不能为空")
    @Length(min = 1,max = 32,message = "你的名字有32个字符???")
    private String name;
    //密码
    @NotEmpty(message = "密码不能为空")
    @Length(min = 1,max = 32,message = "密码太长拉!")
    private String password;
    //年级
    @NotEmpty(message = "年级不能为空")
    @Length(max = 3,message = "年级不应该有那么长把?")
    private String grade;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setGarde(grade);
        return user;
    }
}
