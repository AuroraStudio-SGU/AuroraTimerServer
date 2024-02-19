package com.aurora.day.auroratimerservernative.servicelmpl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.mapper.DutyListMapper;
import com.aurora.day.auroratimerservernative.pojo.WeeklyDustList;
import com.aurora.day.auroratimerservernative.service.IDutyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class DutyServiceImpl implements IDutyService {
    private final DutyListMapper dutyListMapper;

    @Override
    public boolean pushDutyList(String wed,String sun) {
        long currentCount =  dutyListMapper.selectCount(null);
        if(currentCount > TimerConfig.DutySize){
            QueryWrapper<WeeklyDustList> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("create_time").last("limit 1");
            WeeklyDustList temp = dutyListMapper.selectOne(queryWrapper);
            dutyListMapper.deleteById(temp);
        }
        return dutyListMapper.insert(new WeeklyDustList(wed,sun, DateTime.now())) == 1;
    }

    @Override
    public WeeklyDustList getNewestDust() {
        QueryWrapper<WeeklyDustList> queryWrapper = new QueryWrapper<>();
        Date now = DateUtil.date();
        //截止本周周日19点的值日表
        String start = DateUtil.beginOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        String end = DateUtil.endOfWeek(now).setField(DateField.HOUR,19).toString(DatePattern.NORM_DATE_PATTERN);
        queryWrapper.between("create_time",start,end)
                .orderByDesc("create_time")
                .last("limit 1");
        WeeklyDustList dustList = dutyListMapper.selectOne(queryWrapper);
        if(dustList==null){
            dustList = new WeeklyDustList("待发布","待发布",now);
            dustList.setId(-1);
        }
        return dustList;
    }
}
