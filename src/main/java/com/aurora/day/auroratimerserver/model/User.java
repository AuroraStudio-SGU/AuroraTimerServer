package com.aurora.day.auroratimerserver.model;


import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table("users")
public class User extends Model<User> {
    //主键 学号
    @Id(keyType = KeyType.None)
    private String id;
    //姓名
    private String name;
    //年级
    private String garde;
    //组别
    private String workGroup;
    //专业
    private String major;
    //密码
    private String password;
    //头像url
    private String avatar;
    //权限值
    private Integer priv;
    //是否为管理员
    private Boolean admin;
    //是否退休
    private Boolean afk;
    //未完成打卡目标次数
    private Integer unfinishedCount;
    //本周减时时长
    @Deprecated
    private Long reduceTime;
}
