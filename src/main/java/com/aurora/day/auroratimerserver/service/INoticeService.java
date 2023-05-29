package com.aurora.day.auroratimerserver.service;

import com.aurora.day.auroratimerserver.pojo.Notice;

public interface INoticeService {

    /**
     * 插入新的公告
     * @param notice 公告实体类
     * @return 是否插入成功
     */
    boolean insertNotice(Notice notice);

    /**
     * 获取本周的公告
     * @return 公告实体类
     */
    Notice getCurrentNotice();

}
