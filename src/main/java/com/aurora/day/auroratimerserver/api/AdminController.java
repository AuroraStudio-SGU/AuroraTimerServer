package com.aurora.day.auroratimerserver.api;

import com.aurora.day.auroratimerserver.annotation.AdminRequired;
import com.aurora.day.auroratimerserver.model.Notice;
import com.aurora.day.auroratimerserver.model.ReduceTime;
import com.aurora.day.auroratimerserver.model.Result;
import com.aurora.day.auroratimerserver.model.TargetTime;
import com.aurora.day.auroratimerserver.model.Term;
import com.aurora.day.auroratimerserver.schame.req.SetDutyReq;
import com.aurora.day.auroratimerserver.services.IDutyService;
import com.aurora.day.auroratimerserver.services.INoticeService;
import com.aurora.day.auroratimerserver.services.IReduceTimeService;
import com.aurora.day.auroratimerserver.services.ITargetTimeService;
import com.aurora.day.auroratimerserver.services.ITermService;
import com.aurora.day.auroratimerserver.services.IUserService;
import com.aurora.day.auroratimerserver.utils.WeekUtil;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Param;
import org.noear.solon.annotation.Path;
import org.noear.solon.annotation.Post;
import org.noear.solon.annotation.Put;
import org.noear.solon.validation.annotation.Max;
import org.noear.solon.validation.annotation.Min;
import org.noear.solon.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@AdminRequired
@Mapping("admin")
public class AdminController extends BaseController {

    @Inject
    IDutyService dutyService;
    @Inject
    IUserService userService;
    @Inject
    ITermService termService;
    @Inject
    IReduceTimeService reduceTimeService;
    @Inject
    INoticeService noticeService;
    @Inject
    ITargetTimeService targetTimeService;

    @Get
    @Mapping
    public String pingAsAdmin() {
        return "pong as Admin";
    }

    /**
     * 设置本周的值日人员
     */
    @Post
    @Mapping("duty/set")
    public Result<String> setDutyList(
            @Validated SetDutyReq req
    ) {
        return Result.success(dutyService.InsertDutySchedule(req));
    }

    /**
     * 设置本周公告
     *
     * @param notice 公告实体
     * @return 是否设置成功
     */
    @Post
    @Mapping("notice/set")
    public Result<String> setNotice(
            @Validated Notice notice
    ) {
        notice.setUpdateTime(LocalDateTime.now());
        if (notice.getWeekIdentifier() == null) notice.setWeekIdentifier(WeekUtil.getCurrentWeekIdentifier());
        return Result.success(noticeService.InsertOrUpdateNotice(notice));
    }

    /**
     * 为成员设置本周减时时长
     *
     * @param id   学号
     * @param time 需要减免的时长（单位小时)
     */
    @Post
    @Mapping("user/reduceTime/set")
    public Result<String> reduceTimeSet(
            @Param String id,
            @Param @Min(1) @Max(Integer.MAX_VALUE) Integer time
    ) {
        return Result.success(true);
    }

    /**
     * 为本周设置新的打卡目标
     * @param time 目标时间，单位小时
     */
    @Put
    @Mapping("target/set")
    public Result<String> setTargetTime(
            @Param @Max(100) BigDecimal time
    ) {
        TargetTime targetTime = new TargetTime(
                WeekUtil.getCurrentWeekIdentifier(),
                getCurrentUserId(),
                time
        );
        return Result.success(targetTimeService.insertOrUpdateTargetTime(targetTime));
    }

    /**
     * 设置某人本周的减时
     * @param uid 学号
     * @param time 时间，单位小时
     */
    @Put
    @Mapping("reduce/{uid}")
    public Result<String> reduceUserTime(
            @Path("uid") String uid,
            @Param("time") @Min(1) @Max(100) BigDecimal time
    ) {
        if (!userService.isExitUser(uid)) return Result.fail("用户不存在");
        ReduceTime reduceTime = new ReduceTime(
                WeekUtil.getCurrentWeekIdentifier(),
                uid,
                getCurrentUserId(),
                time
        );
        return Result.success(reduceTimeService.insertOrUpdateReduceTime(reduceTime));
    }

    /**
     * 更新或插入 学期信息
     * @param term 学期信息
     */
    @Put
    @Mapping("term/set")
    public Result<String> setTermManual(
            @Validated Term term
    ) {
        term.setUpdateTime(LocalDateTime.now());
        term.setDays((int) term.getStart().until(term.getEnd(), ChronoUnit.DAYS));
        return Result.success(termService.insertOrUpdateTerm(term));
    }
}
