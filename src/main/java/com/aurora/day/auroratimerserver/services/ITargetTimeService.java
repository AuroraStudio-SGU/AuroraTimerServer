package com.aurora.day.auroratimerserver.services;

import com.aurora.day.auroratimerserver.model.TargetTime;

import java.util.List;

public interface ITargetTimeService {

    TargetTime queryNewestTargetTime();

    boolean insertOrUpdateTargetTime(TargetTime time);
}
