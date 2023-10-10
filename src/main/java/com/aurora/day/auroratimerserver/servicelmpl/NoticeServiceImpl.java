package com.aurora.day.auroratimerserver.servicelmpl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.id.NanoId;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.StaticLog;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.mapper.NoticeMapper;
import com.aurora.day.auroratimerserver.pojo.Notice;
import com.aurora.day.auroratimerserver.service.INoticeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements INoticeService {
    private final NoticeMapper noticeMapper;

    @Override
    public boolean insertNotice(Notice notice) {
        notice.setNoticeId(NanoId.randomNanoId(16));
        long currentLength = noticeMapper.selectCount(null);
        if(currentLength > TimerConfig.noticeSize){
//            StaticLog.info("当前公告个数:{},超出设定值:{},开始删除最早的公告",currentLength,TimerConfig.noticeSize);
            QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
            noticeQueryWrapper.orderByAsc("update_time").last("limit 1");//not safe sql
            noticeMapper.delete(noticeQueryWrapper);
        }
        return noticeMapper.insert(notice)==1;
    }

    @Override
    public Notice getCurrentNotice() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        Date now = DateUtil.date();
        String start = DateUtil.beginOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        String end = DateUtil.endOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        wrapper.between("update_time",start,end)
                .orderByDesc("update_time")
                .last("limit 1");
        return noticeMapper.selectOne(wrapper);
    }

    @Override
    public List<Notice> getAllNotice() {
        return noticeMapper.selectList(null);
    }

    @Override
    public boolean updateNotice(Notice notice) {
        return noticeMapper.updateById(notice)==1;
    }
}
