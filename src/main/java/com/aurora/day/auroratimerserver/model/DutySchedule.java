package com.aurora.day.auroratimerserver.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noear.snack.annotation.ONodeAttr;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Table("duty_schedule")
public class DutySchedule {
    @Id(keyType = KeyType.None)
    //周标识符 用int表示 例如202413（2024年第13周）
    private Integer weekIdentifier;
    //周三值日人
    private String wednesday;
    //周日值日人
    private String sunday;
    //更新时间
    @ONodeAttr(name = "createTime")
    private LocalDateTime updatedTime;

    public DutySchedule(Integer weekIdentifier) {
        this.weekIdentifier = weekIdentifier;
        this.updatedTime = LocalDateTime.now();
        this.sunday = "未设定";
        this.wednesday = "未设定";
    }
}
