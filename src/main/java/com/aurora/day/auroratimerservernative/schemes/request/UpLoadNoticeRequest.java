package com.aurora.day.auroratimerservernative.schemes.request;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.id.NanoId;
import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.pojo.Notice;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpLoadNoticeRequest {
    @NotEmpty(message = "id不能为空")
    private String user_id;
    private String notice_id;
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
