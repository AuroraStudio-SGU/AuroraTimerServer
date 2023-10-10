package com.aurora.day.auroratimerserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("weekly_dust_list")
public class WeeklyDustList {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "wednesday")
    private String wednesday;
    @TableField(value = "sunday")
    private String sunday;

    private Date createTime;


    public WeeklyDustList(String wednesday, String sunday, Date createTime) {
        this.wednesday = wednesday;
        this.sunday = sunday;
        this.createTime = createTime;
    }
}
