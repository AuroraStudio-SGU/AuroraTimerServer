package com.aurora.day.auroratimerservernative.pojo;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("target_time_list")
public class TargetTime {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 单位:小时
     */
    private float targetTime;
    private Date createTime;

    public TargetTime(float targetTime) {
        this.targetTime = targetTime;
        this.createTime = DateTime.now();
    }
}
