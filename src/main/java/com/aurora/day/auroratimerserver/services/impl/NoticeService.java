package com.aurora.day.auroratimerserver.services.impl;

import com.aurora.day.auroratimerserver.mapper.NoticeMapper;
import com.aurora.day.auroratimerserver.model.Notice;
import com.aurora.day.auroratimerserver.services.INoticeService;
import com.aurora.day.auroratimerserver.utils.WeekUtil;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class NoticeService implements INoticeService {

    @Inject
    NoticeMapper noticeMapper;

    @Override
    public Notice queryNewestNotice() {
        int week = WeekUtil.getCurrentWeekIdentifier();
        Notice notice = noticeMapper.selectOneById(week);
        if(notice == null) {
            notice = new Notice(week,"","本周还没有发布公告");
        }
        return notice;
    }

    @Override
    public boolean InsertOrUpdateNotice(Notice notice) {
        return noticeMapper.insertOrUpdate(notice) == 1;
    }
}
