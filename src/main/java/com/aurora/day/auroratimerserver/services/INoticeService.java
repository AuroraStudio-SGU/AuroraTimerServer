package com.aurora.day.auroratimerserver.services;

import com.aurora.day.auroratimerserver.model.Notice;

public interface INoticeService {

    /**
     * 查询本周的公告
     * @return 公告，如果不存在则返回非存储在数据库内的空公告
     */
    Notice queryNewestNotice();

    /**
     * 更新公告 如果本周没有公告则进行插入，否则进行更新
     * @param notice 公告
     * @return 是否更新成功
     */
    boolean InsertOrUpdateNotice(Notice notice);
}
