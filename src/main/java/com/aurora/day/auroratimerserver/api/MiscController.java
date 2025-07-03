package com.aurora.day.auroratimerserver.api;

import com.aurora.day.auroratimerserver.model.DutySchedule;
import com.aurora.day.auroratimerserver.model.Notice;
import com.aurora.day.auroratimerserver.model.Result;
import com.aurora.day.auroratimerserver.model.TargetTime;
import com.aurora.day.auroratimerserver.model.Term;
import com.aurora.day.auroratimerserver.services.IDutyService;
import com.aurora.day.auroratimerserver.services.INoticeService;
import com.aurora.day.auroratimerserver.services.ITargetTimeService;
import com.aurora.day.auroratimerserver.services.ITermService;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;

import java.util.List;

/**
 * 一些杂项接口
 */
public class MiscController extends BaseController {

    @Inject
    IDutyService dutyService;
    @Inject
    INoticeService noticeService;
    @Inject
    ITargetTimeService targetTimeService;
    @Inject
    ITermService termService;

    /**
     * 测试服务器状态
     */
    @Get
    @Mapping("ping")
    public String ping() {
        return "pong";
    }

    /**
     * 获取本周的值日表
     */
    @Get
    @Mapping("duty/query")
    public Result<DutySchedule> getDustList() {
        return Result.success(dutyService.getCurrentWeekSchedule());
    }

    /**
     * 获取本周的公告
     */
    @Get
    @Mapping("notice")
    public Result<Notice> getNotice() {
        return Result.success(noticeService.queryNewestNotice());
    }

    /**
     * 查询所有学期信息
     */
    @Get
    @Mapping("terms")
    public Result<List<Term>> queryAllTerms() {
        return Result.success(termService.getAllTerms());
    }

    /**
     * 获取当前学期信息
     */
    @Get
    @Mapping("term")
    public Result<Term> queryCurrentTerm() {
        return Result.success(termService.getCurrentTerm());
    }

    /**
     * 获取本周的目标时长
     */
    @Get
    @Mapping("target")
    public Result<TargetTime> queryCurrentTargetTime() {
        return Result.success(targetTimeService.queryNewestTargetTime());
    }


}
