package com.aurora.day.auroratimerserver.pojo;

import cn.hutool.core.bean.BeanUtil;
import com.aurora.day.auroratimerserver.vo.UserVo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;
    private String avatar;
    private boolean admin;
    private boolean afk;
    private long reduceTime;
    private int unfinishedCount;
    private String major;
    private String grade;
    @TableField("work_group")
    private String work_group;

    public UserVo toVo() {
        return BeanUtil.toBean(this, UserVo.class);
    }

}
