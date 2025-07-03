package com.aurora.day.auroratimerserver.services;

import com.aurora.day.auroratimerserver.model.ReduceTime;

import java.util.List;

public interface IReduceTimeService {

    List<ReduceTime> queryReduceTimeListByWeekId(int weekId);

    ReduceTime queryCurrentWeekReduceTimeByUserId(String uid);

    ReduceTime queryReduceTimeByUserId(String uid, int weekId);

    List<ReduceTime> queryAll();

    boolean insertOrUpdateReduceTime(ReduceTime reduceTime);

}
