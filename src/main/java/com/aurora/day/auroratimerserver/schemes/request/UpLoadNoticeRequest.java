package com.aurora.day.auroratimerserver.schemes.request;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.id.NanoId;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.pojo.Notice;
import lombok.Data;
//TODO scheme规范
@Data
public class UpLoadNoticeRequest {
    private String user_id;
    private String notice_id;
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
