package com.aurora.day.auroratimerserver.services.impl;

import com.aurora.day.auroratimerserver.mapper.ReduceTimeMapper;
import com.aurora.day.auroratimerserver.model.ReduceTime;
import com.aurora.day.auroratimerserver.services.IReduceTimeService;
import com.aurora.day.auroratimerserver.utils.WeekUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;

@Component
public class ReduceTimeService implements IReduceTimeService {

    @Inject
    ReduceTimeMapper reduceTimeMapper;

    @Override
    public List<ReduceTime> queryReduceTimeListByWeekId(int weekId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq(ReduceTime::getWeekIdentifier, weekId);
        return reduceTimeMapper.selectListByQuery(qw);
    }

    @Override
    public ReduceTime queryCurrentWeekReduceTimeByUserId(String uid) {
        return queryReduceTimeByUserId(uid, WeekUtil.getCurrentWeekIdentifier());
    }

    @Override
    public ReduceTime queryReduceTimeByUserId(String uid, int weekId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq(ReduceTime::getWeekIdentifier, weekId)
                .eq(ReduceTime::getTargetId, uid);
        return reduceTimeMapper.selectOneByQuery(qw);
    }

    @Override
    public List<ReduceTime> queryAll() {
        return reduceTimeMapper.selectAll();
    }

    @Override
    public boolean insertOrUpdateReduceTime(ReduceTime reduceTime) {
        return reduceTimeMapper.insertOrUpdate(reduceTime, true) > 0;
    }
}
