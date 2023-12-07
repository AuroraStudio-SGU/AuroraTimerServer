package com.aurora.day.auroratimerservernative.cache;

import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerservernative.pojo.UserOnlineTime;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RankCache {

    private List<UserOnlineTime> list;
    private Date updateTime;

    public boolean isCurrentWeek() {
        if (updateTime == null) return false;
        return DateUtil.isSameWeek(updateTime, new Date(), true);
    }

    public boolean isEmpty(){
        return updateTime==null;
    }
}
