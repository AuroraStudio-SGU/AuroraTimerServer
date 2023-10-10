package com.aurora.day.auroratimerserver.service;

import com.aurora.day.auroratimerserver.pojo.Notice;
import org.springframework.lang.Nullable;

import java.util.List;

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
    @Nullable
    Notice getCurrentNotice();

    /**
     * 获取全部公告
     * @return 公告列表
     */
    List<Notice> getAllNotice();

    /**
     * 更新公告内容
     * @param notice 公告实体类
     * @return 是否更新成功。
     */
    boolean updateNotice(Notice notice);

}
