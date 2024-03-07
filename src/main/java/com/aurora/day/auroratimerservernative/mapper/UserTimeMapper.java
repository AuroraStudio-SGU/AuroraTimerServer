package com.aurora.day.auroratimerservernative.mapper;


import com.aurora.day.auroratimerservernative.pojo.UserOnlineTime;
import com.aurora.day.auroratimerservernative.pojo.UserTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTimeMapper extends BaseMapper<UserTime> {
    //TODO这里的sum计算未来还是使用cache的方式进行。
    //↑cache不了一点，基本上都要频繁刷新的
    @Select("select u.id as 'id' , u.name as 'name' , u.reduce_time as 'reduceTime', u.unfinished_count as 'unfinishedCount', u.grade as 'grade',u.avatar as 'avatar', u.priv as 'priv', u.work_group as 'workGroup' , " +
            "COALESCE(w.weekTime,0) as 'weekTime' ," +
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
            "where u.afk = 0 " +
            "or weekTime!=0 " +
            "order by w.weekTime desc ")
    List<UserOnlineTime> getRankTime(String TermStart, String TermEnd, String WeekStart, String WeekEnd);

    @Select("select SUM(ut.online_time) from user_online_time as ut where ut.record_date between #{week_start} and #{week_end} and user_id=#{id}")
    Long queryUserWeekTime(String id, String week_start, String week_end);

    @Select("select t.user_id as 'user_id',t.online_time as 'onlineTime', t.record_date as 'recordDate' from user_online_time as t " +
            "where t.user_id=#{id} and record_date between #{start} and #{end} " +
            "order by t.record_date")
    List<UserTime> queryTime(String id, String start, String end);
}
