package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerserver.cache.RankCache;
import com.aurora.day.auroratimerserver.exceptions.TimeServicesException;
import com.aurora.day.auroratimerserver.pojo.WeeklyDustList;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.eums.ResponseState;
import com.aurora.day.auroratimerserver.service.IDutyService;
import com.aurora.day.auroratimerserver.service.INoticeService;
import com.aurora.day.auroratimerserver.service.IUserTimeService;
import com.aurora.day.auroratimerserver.vo.UserOnlineTime;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
@Tag(name = "TimerController", description = "计时器接口")
public class TimerController {

    private final Log logger = LogFactory.get(TimerController.class);
    private final IUserTimeService userTimeService;

    private final INoticeService noticeService;
    private final IDutyService dutyService;


    //添加计时时长
    @GetMapping("/timer/addTime/{id}")
    public R addTime(@PathVariable(name = "id") String id) {
        if (StrUtil.isBlankIfStr(id)) return R.error("参数为空");
        try {
            userTimeService.addTime(id, 60);
            return R.OK(userTimeService.getUserWeekTimeById(id));
        } catch (TimeServicesException e) {
            logger.warn("数据添加计时数据失败");
            return R.error(ResponseState.ERROR,"添加计时失败");
        }
    }

    //查询某周的打卡情况。
    @GetMapping("/timer/lastXWeek/{x}")
    public R lastXWeek(@PathVariable(name = "x") String x) {
        if (!StrUtil.isNumeric(x)) return R.error(ResponseState.IllegalArgument,"非法参数!");
        return R.OK(userTimeService.getTimeRank(Integer.parseInt(x)));
    }

    private final RankCache TopCache = new RankCache();

    //返回前一周打卡前三的。
    @GetMapping("/timer/top3")
    public R top3() {
        if (TopCache.isEmpty() || !TopCache.isCurrentWeek()) {
            TopCache.setList(userTimeService.getTimeRank(1).subList(0, 3));
            TopCache.setUpdateTime(new Date());
        }
        return R.OK(TopCache.getList());
    }

    private final RankCache LastCache = new RankCache();

    /*
        做了个小缓存，现在的逻辑是返回不满足打卡要求的最后三位，算上减时的
     */
    @GetMapping("/timer/last3")
    public R last_3() {
        if (LastCache.isEmpty() || !LastCache.isCurrentWeek()) {
            long currentTargetTime =(long)userTimeService.getTargetTime().getTargetTime()*3600L;
            List<UserOnlineTime> list = userTimeService.getTimeRank(1);
            List<UserOnlineTime> LastRank = new ArrayList<>(3);
            for (int i = list.size()-1; i >=0 ; i--) {
                UserOnlineTime userTime = list.get(i);
                long time = userTime.getWeekTime() + userTime.getReduceTime();
                if(Math.abs(time)<currentTargetTime){
                    LastRank.add(userTime);
                    if(LastRank.size()==3) break;
                }
            }
            LastCache.setList(LastRank);
            LastCache.setUpdateTime(new Date());
        }
        return R.OK(LastCache.getList());
    }

    //心跳包
    @GetMapping("/ping")
    public R heartPackage() {
        return R.OK("pong");
    }

    @GetMapping("/timer/getNotice")
    public R getNotice() {
        return R.OK(noticeService.getCurrentNotice());
    }

    @GetMapping("/getDustList")
    public R getDustList() {
        WeeklyDustList dust = dutyService.getNewestDust();
        if (dust == null) {
            return R.error(ResponseState.DateBaseError,"没有值日表");
        }
        return R.OK(dust);
    }

    @GetMapping("/getTargetTime")
    public R getTargetTime() {
        return R.OK(userTimeService.getTargetTime());
    }

    @GetMapping("/timer/getWeekTime/{id}")
    public R getWeekTime(@PathVariable("id")String id){
        Long time = userTimeService.getUserWeekTimeById(id);
        if(time==null) time=0L;
        return R.OK(time);
    }

}
