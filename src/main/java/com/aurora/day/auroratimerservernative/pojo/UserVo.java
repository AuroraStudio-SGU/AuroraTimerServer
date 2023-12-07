package com.aurora.day.auroratimerservernative.pojo;

import com.aurora.day.auroratimerservernative.config.TimerConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RegisterReflectionForBinding(UserVo.class)
public class UserVo implements Serializable {
    private String id;//学号
    private String name;//姓名
    private String avatar;//头像地址
    private boolean admin;//是否为管理员
    private long currentWeekTime;//本周打卡时长
    private boolean afk;//是否退休
    private String token;//token
    private String major;//专业
    private String grade;//年级
    private String workGroup;//方向
    private int priv;//权限，详细请查看PrivilegeEnum

    public UserVo() {
        this.avatar = TimerConfig.avatarDefaultUrl;
        this.admin = false;
        this.afk = false;
        this.priv = 0;
    }

}
