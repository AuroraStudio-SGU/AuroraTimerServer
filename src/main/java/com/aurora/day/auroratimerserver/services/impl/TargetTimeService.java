package com.aurora.day.auroratimerserver.services.impl;

import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.mapper.TargetTimeMapper;
import com.aurora.day.auroratimerserver.model.TargetTime;
import com.aurora.day.auroratimerserver.services.ITargetTimeService;
import com.aurora.day.auroratimerserver.utils.WeekUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TargetTimeService implements ITargetTimeService {
    @Inject
    TargetTimeMapper targetTimeMapper;

    @Override
    public TargetTime queryNewestTargetTime() {
        TargetTime time = targetTimeMapper.selectOneById(WeekUtil.getCurrentWeekIdentifier());
        if (time == null) {
            time = new TargetTime(WeekUtil.getCurrentWeekIdentifier(), "", TimerConfig.defaultTargetTime);
            targetTimeMapper.insert(time);
            AtomicBoolean cleaning = new AtomicBoolean(false);
            if (!cleaning.get()) {
                cleaning.set(true);
                Thread.ofVirtual().start(this::cleanList).start();
            }
        }
        return time;
    }

    @Override
    public boolean insertOrUpdateTargetTime(TargetTime time) {
        return targetTimeMapper.insertOrUpdate(time) > 0;
    }

    //检查并删除之前的记录
    public void cleanList() {
        QueryWrapper wp = new QueryWrapper();
        wp.orderBy(TargetTime::getWeekIdentifier);
        List<TargetTime> list = targetTimeMapper.selectListByQuery(wp);
        if (list.size() > TimerConfig.targetTimeCount) {
            List<Integer> subIds = list.subList(TimerConfig.targetTimeCount - 1, list.size()).stream().map(TargetTime::getWeekIdentifier).toList();
            targetTimeMapper.deleteBatchByIds(subIds);
        }
    }
}
