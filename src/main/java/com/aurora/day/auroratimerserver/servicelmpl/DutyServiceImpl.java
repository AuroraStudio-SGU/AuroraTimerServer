package com.aurora.day.auroratimerserver.servicelmpl;

import cn.hutool.core.date.DateTime;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.mapper.DutyListMapper;
import com.aurora.day.auroratimerserver.pojo.WeeklyDustList;
import com.aurora.day.auroratimerserver.service.IDutyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



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
        queryWrapper.orderByDesc("create_time").last("limit 1");
        return dutyListMapper.selectOne(queryWrapper);
    }
}
