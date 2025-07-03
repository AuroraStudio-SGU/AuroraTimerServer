package com.aurora.day.auroratimerserver.mapper;

import com.aurora.day.auroratimerserver.model.TermUserTime;
import com.aurora.day.auroratimerserver.model.UserOnlineTime;
import com.aurora.day.auroratimerserver.model.vo.UserTimeReport;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserOnlineTimeMapper extends BaseMapper<UserOnlineTime> {

    @Select("""
            select COALESCE(w.week_time,0) as week_time , u.*
            from users as u
                        left join (
                            select user_id, SUM(online_time) as week_time
                            from user_online_time
                            where record_date between CAST(#{start} AS DATE) and CAST(#{end} AS DATE)
                            group by user_id
                        ) w on u.id = w.user_id
            where u.afk = false
            order by week_time desc
            """)
    List<UserTimeReport> queryWeekRankTime(
            @Param("start") String start,
            @Param("end") String end
    );
    @Select("""
            select COALESCE(w.week_time,0) as week_time
            from users as u
                        left join (
                            select user_id, SUM(online_time) as week_time
                            from user_online_time
                            where record_date between CAST(#{start} AS DATE) and CAST(#{end} AS DATE)
                            group by user_id
                        ) w on u.id = w.user_id
            where u.afk = false
            and u.id = #{uid}
            order by week_time desc
            """)
    UserTimeReport queryUserTimeReport(
            @Param("start") String start,
            @Param("end") String end,
            @Param("uid") String uid
    );

    @MapKey("uid")
    @Select("""
            select u.id as uid , COALESCE(w.totalTime,0) as totalTime
            from users as u
                        left join (
                            select user_id, SUM(online_time) as totalTime
                            from user_online_time
                            where record_date between CAST(#{start} AS DATE) and CAST(#{end} AS DATE)
                            group by user_id
                        ) w on u.id = w.user_id
            where u.afk = false
            order by totalTime desc
            """)
    List<TermUserTime> queryTermRankTime(
            @Param("start") String start,
            @Param("end") String end
    );
    @Select("""
            select COALESCE(w.week_time,0) as week_time , u.*
            from users as u
                        left join (
                            select user_id, SUM(online_time) as week_time
                            from user_online_time
                            where record_date between CAST(#{start} AS DATE) and CAST(#{end} AS DATE)
                            group by user_id
                        ) w on u.id = w.user_id
            where u.afk = false
            order by week_time desc
            limit #{limit}
            """)
    List<UserTimeReport> queryLimitRankTopReport(
            @Param("start") String start,
            @Param("end") String end,
            @Param("limit") int limit
    );
    @Select("""
            select COALESCE(w.week_time,0) as week_time , u.*
            from users as u
                        left join (
                            select user_id, SUM(online_time) as week_time
                            from user_online_time
                            where record_date between CAST(#{start} AS DATE) and CAST(#{end} AS DATE)
                            group by user_id
                        ) w on u.id = w.user_id
            where u.afk = false
            order by week_time
            limit #{limit}
            """)
    List<UserTimeReport> queryLimitRankDownReport(
            @Param("start") String start,
            @Param("end") String end,
            @Param("limit") int limit
    );
}
