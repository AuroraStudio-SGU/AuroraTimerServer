package com.aurora.day.auroratimerserver.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Table("target_time_list")
public class TargetTime {
    @Id(keyType = KeyType.None)
    //周标识符 用int表示 例如202413（2024年第13周）
    private Integer weekIdentifier;
    //上传者
    private String userId;
    //目标时长(单位小时)
    private BigDecimal targetTime;
    //更新时间
    private LocalDateTime updateTime;

    public TargetTime(Integer weekIdentifier, String userId, BigDecimal targetTime) {
        this.weekIdentifier = weekIdentifier;
        this.userId = userId;
        this.targetTime = targetTime;
        this.updateTime = LocalDateTime.now();
    }
}
