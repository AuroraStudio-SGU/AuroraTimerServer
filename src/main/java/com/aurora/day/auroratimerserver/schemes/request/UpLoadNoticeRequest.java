package com.aurora.day.auroratimerserver.schemes.request;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.id.NanoId;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.pojo.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpLoadNoticeRequest {
    @Schema(name = "user_id",description = "上传者id",requiredMode = Schema.RequiredMode.REQUIRED)
    private String user_id;
    @Schema(name = "notice_id",description = "公告id,上传时不需要传入",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String notice_id;
    @Schema(name = "context",description = "上传内容",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "内容不能为空")
    private String context;

    public Notice toNotice(){
        return new Notice(NanoId.randomNanoId(TimerConfig.idLength),user_id,context,DateUtil.date());
    }
    public Notice toNotice(boolean isCreateNew) {
        if (isCreateNew) {return this.toNotice();}
        else {
            return new Notice(notice_id, user_id, context, DateUtil.date());
        }

    }

}
