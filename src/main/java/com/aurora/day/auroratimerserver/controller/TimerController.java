package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.util.StrUtil;
import com.aurora.day.auroratimerserver.pojo.WeeklyDustList;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.service.IDutyService;
import com.aurora.day.auroratimerserver.service.INoticeService;
import com.aurora.day.auroratimerserver.service.IUserTimeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
@Tag(name = "TimerController", description = "计时器接口")
public class TimerController {

    private final IUserTimeService userTimeService;

    private final INoticeService noticeService;
    private final IDutyService dutyService;


    //添加计时时长
    @GetMapping("/timer/addTime/{id}/{time}")
    public R addTime(@PathVariable(name = "id")String id,@PathVariable(name = "time")String time){
        if(StrUtil.isBlankIfStr(id) || StrUtil.isBlankIfStr(time)) return R.error("参数为空");
        if(!StrUtil.isNumeric(time)) return R.error("非法参数");
        if(userTimeService.addTime(id,Integer.parseInt(time))) return R.OK();
        else return R.error("添加计时失败");
    }
    //查询某周的打卡情况。
    @GetMapping("/timer/lastXWeek/{x}")
    public R lastXWeek(@PathVariable(name = "x")String x){
        if(!StrUtil.isNumeric(x)) return R.error("非法参数!");
        return R.OK(userTimeService.getTimeRank(Integer.parseInt(x)));
    }
    //返回前一周打卡前三的。
    @GetMapping("/timer/top3")
    public R top3(){
        return R.OK(userTimeService.getTimeRank(1).subList(0,3));
    }
    //心跳包
    @GetMapping("/ping")
    public R heartPackage(){
        return R.OK("pong");
    }

    @GetMapping("/timer/getNotice")
    public R getNotice(){
        return R.OK(noticeService.getCurrentNotice());
    }

    @GetMapping("/getDustList")
    public R getDustList(){
        WeeklyDustList dust = dutyService.getNewestDust();
        if(dust==null){
            return R.error("没有值日表");
        }
        return R.OK(dust);
    }

    @GetMapping("/getTargetTime")
    public R getTargetTime(){
        return R.OK(userTimeService.getTargetTime());
    }

}
