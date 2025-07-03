package com.aurora.day.auroratimerserver.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("reduce_time")
public class ReduceTime {
    //周标识符 用int表示 例如202413（2024年第13周）
    @Id(keyType = KeyType.None)
    private Integer weekIdentifier;
    //本周需要被减时的用户
    @Id(keyType = KeyType.None)
    private String targetId;
    //设置减时的用户
    private String userId;
    //减时时长(单位小时)
    private BigDecimal reduceTime;
}
