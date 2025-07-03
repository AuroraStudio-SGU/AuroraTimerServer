package com.aurora.day.auroratimerserver.services.impl;

import com.aurora.day.auroratimerserver.mapper.DutyMapper;
import com.aurora.day.auroratimerserver.model.DutySchedule;
import com.aurora.day.auroratimerserver.schame.req.SetDutyReq;
import com.aurora.day.auroratimerserver.services.IDutyService;
import com.aurora.day.auroratimerserver.utils.WeekUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class DutyService implements IDutyService {

    @Inject
    DutyMapper dutyMapper;

    @Override
    public DutySchedule getCurrentWeekSchedule() {
        int week = WeekUtil.getCurrentWeekIdentifier();
        DutySchedule result = dutyMapper.selectOneById(week);
        if (result == null) {
            result = new DutySchedule(week);
            dutyMapper.insert(result);
        }
        return result;
    }

    @Override
    public boolean InsertDutySchedule(SetDutyReq req) {
        DutySchedule schedule = new DutySchedule(WeekUtil.getCurrentWeekIdentifier());
        schedule.setWednesday(req.getWed());
        schedule.setSunday(req.getSun());
        return dutyMapper.insertOrUpdate(schedule) == 1;
    }
}
