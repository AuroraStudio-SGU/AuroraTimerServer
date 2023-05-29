package com.aurora.day.auroratimerserver.schemes.request;

import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerserver.pojo.Notice;
import lombok.Data;

@Data
public class UpLoadNoticeRequest {

    private String id;

    private String context;

    private String targetTime;

    public Notice toNotice(){
        return new Notice(id,context, DateUtil.parseTime(targetTime),DateUtil.date());
    }

}
