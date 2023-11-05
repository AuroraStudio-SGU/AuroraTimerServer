package com.aurora.day.auroratimerserver.mapper;

import com.aurora.day.auroratimerserver.pojo.OldUserTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OldUserTimeMapper extends BaseMapper<OldUserTime> {

    @Select("select * from useronlinetime as t where today_date between #{start} and #{end} order by t.id")
    List<OldUserTime> queryOldTimeByDate(String start,String end);
}
