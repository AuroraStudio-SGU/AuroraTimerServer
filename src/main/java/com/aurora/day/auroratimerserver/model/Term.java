package com.aurora.day.auroratimerserver.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.noear.solon.validation.annotation.NotEmpty;
import org.noear.solon.validation.annotation.NotNull;
import org.noear.solon.validation.annotation.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Table("terms")
public class Term {
    //学期标识符 例如20241为2024第一学期(2023-9~2024-2)
    @Id(keyType = KeyType.None)
    @NotEmpty("学期标识符不能为空")
    @Pattern(value = "^\\d{6}$",message = "标识符格式不对")
    private Integer id;
    //学期长度
    private Integer days;
    //学期开始日期
    @NotNull("开始日期不能为空")
    private LocalDate start;
    @NotNull("结束日期不能为空")
    //学期结束日期
    private LocalDate end;
    //更新时间
    private LocalDateTime updateTime;
    //名字
    @NotEmpty("名字不能为空")
    private String name;
}
