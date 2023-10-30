package com.aurora.day.auroratimerserver.schemes.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class updateUserRequest {
    @Schema(name = "id",description = "用户id")
    private String id;
    @Schema(name = "name",description = "名字")
    private String name;
    @Schema(name = "avatar",description = "头像地址")
    private String avatar;
    @Schema(name = "major",description = "专业")
    private String major;
    @Schema(name = "grade",description = "年级")
    private String grade;
    @Schema(name = "work_group",description = "方向")
    private String work_group;
    @Schema(name = "afk",description = "是否退休")
    private boolean afk;
}
