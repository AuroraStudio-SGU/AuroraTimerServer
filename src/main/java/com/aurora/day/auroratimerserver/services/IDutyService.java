package com.aurora.day.auroratimerserver.services;

import com.aurora.day.auroratimerserver.model.DutySchedule;
import com.aurora.day.auroratimerserver.schame.req.SetDutyReq;

public interface IDutyService {

    DutySchedule getCurrentWeekSchedule();

    boolean InsertDutySchedule(SetDutyReq req);

}
