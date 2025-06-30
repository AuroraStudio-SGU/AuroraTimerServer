package com.aurora.day.auroratimerserver.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Table("terms")
public class Term {
    //学期标识符 例如20241为2024第一学期(2023-9~2024-2)
    @Id(keyType = KeyType.None)
    private Integer id;
    //学期长度
    private Integer days;
    //学期开始日期
    private LocalDate start;
    //学期结束日期
    private LocalDate end;
    //更新时间
    private LocalDateTime updateTime;
    //名字
    private String name;
}
