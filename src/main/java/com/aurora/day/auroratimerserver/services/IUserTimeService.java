package com.aurora.day.auroratimerserver.services;

import com.aurora.day.auroratimerserver.model.Term;
import com.aurora.day.auroratimerserver.model.UserOnlineTime;
import com.aurora.day.auroratimerserver.model.UserTimeBuffer;
import com.aurora.day.auroratimerserver.model.vo.UserTimeReport;

import java.util.List;
import java.util.Map;

public interface IUserTimeService {
    /**
     * 获取今日打卡记录，并封装为缓存
     * 如果不存在则新建并插入到数据库中
     *
     * @param uid 学号
     * @return 封装后的缓存实体类
     */
    UserTimeBuffer getTodayUserTimeAsBuffer(String uid);

    /**
     * 从数据库查询今日打卡记录
     *
     * @param uid 学号
     * @return 数据库实体类
     */
    UserOnlineTime getTodayUserTime(String uid);

    /**
     * 对缓存实体类批量更新到数据库装中
     *
     * @param userTimes 封装的打卡记录
     */
    void batchUpdate(List<UserTimeBuffer> userTimes);

    /**
     * 获取用户本周的打卡时长
     *
     * @param uid 学号
     * @return 本周的打卡时长（单位秒）
     */
    long getUserWeeklyTime(String uid);

    /**
     * 查询前n周的打卡排行
     * (学期统计数据使用缓存）
     *
     * @param weekOffset 周偏移值，代表前n周
     */
    List<UserTimeReport> queryTimeRank(int weekOffset);

    /**
     * 查询某学期下的打卡排行
     * 数据缓存一天
     *
     * @param term 学期
     * @return key 为学号，value为时长（单位秒）
     */
    Map<String, Long> getTermUserTimeMap(Term term);

    /**
     * 获得某周前n名数据
     *
     * @param weekOffset 周偏移
     * @param offset     列表个数，正值为最高前n名，负值为倒数前n
     */
    List<UserTimeReport> getLimitUserTimeRank(int weekOffset, int offset);
}
