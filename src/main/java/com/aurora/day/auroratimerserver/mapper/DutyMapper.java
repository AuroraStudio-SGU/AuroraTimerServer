package com.aurora.day.auroratimerserver.mapper;

import com.aurora.day.auroratimerserver.model.DutySchedule;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DutyMapper extends BaseMapper<DutySchedule> {
}
