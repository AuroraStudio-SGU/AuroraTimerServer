package com.aurora.day.auroratimerserver.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table("notices")
public class Notice {
    @Id(keyType = KeyType.None)
    //周标识符 用int表示 例如202413（2024年第13周）
    private Integer weekIdentifier;
    //上传者
    private String userId;
    //公告内容（MarkDown）格式
    private String notice;
    //更新时间
    private LocalDateTime updateTime;

    public Notice(Integer weekIdentifier, String userId, String notice) {
        this.weekIdentifier = weekIdentifier;
        this.userId = userId;
        this.notice = notice;
        this.updateTime = LocalDateTime.now();
    }
}
