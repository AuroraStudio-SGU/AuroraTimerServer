package com.aurora.day.auroratimerserver.mapper;


import com.aurora.day.auroratimerserver.pojo.UserTime;
import com.aurora.day.auroratimerserver.vo.UserOnlineTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTimeMapper extends BaseMapper<UserTime> {
    //TODO 这里的sum计算未来还是使用cache的方式进行。
    @Select("select u.id as 'id' , u.name as 'name' , u.reduce_time as 'reduceTime', u.unfinished_count as 'unfinishedCount' , COALESCE(w.weekTime,0) as 'weekTime' ," +
            "COALESCE(y.totalTime,0) as 'totalTime'" +
            "from user as u " +
            "left join (" +
            "    select user_id, SUM(online_time) as 'weekTime'" +
            "    from user_online_time" +
            "    where record_date between #{WeekStart} and #{WeekEnd}" +
            "    group by user_id" +
            ") w on u.id = w.user_id " +
            "left join (" +
            "    select user_id, SUM(online_time) as 'totalTime'" +
            "    from user_online_time" +
            "    where record_date between #{TermStart} and #{TermEnd}" +
            "    group by user_id" +
            ") y on u.id = y.user_id " +
            "where u.afk = 0 "+
            "order by w.weekTime desc ")
    List<UserOnlineTime> getRankTime(String TermStart, String TermEnd, String WeekStart, String WeekEnd);

}
