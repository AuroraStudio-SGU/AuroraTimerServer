package com.aurora.day.auroratimerserver.servicelmpl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerserver.mapper.NoticeMapper;
import com.aurora.day.auroratimerserver.pojo.Notice;
import com.aurora.day.auroratimerserver.service.INoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements INoticeService {

    private final NoticeMapper noticeMapper;

    @Override
    public boolean insertNotice(Notice notice) {
        return noticeMapper.insert(notice)==1;
    }

    @Override
    public Notice getCurrentNotice() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        Date now = DateUtil.date();
        String start = DateUtil.beginOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        String end = DateUtil.endOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        wrapper.between("update_time",start,end);
        wrapper.orderByDesc("update_time");
        return noticeMapper.selectList(wrapper).get(0);
    }
}
