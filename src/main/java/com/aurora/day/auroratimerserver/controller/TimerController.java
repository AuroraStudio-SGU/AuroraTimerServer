package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.util.StrUtil;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.servicelmpl.NoticeServiceImpl;
import com.aurora.day.auroratimerserver.servicelmpl.UserTimeServiceImpl;
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

    private final UserTimeServiceImpl userTimeService;

    private final NoticeServiceImpl noticeService;

    @GetMapping("/timer/addTime/{id}/{time}")
    public R addTime(@PathVariable(name = "id")String id,@PathVariable(name = "time")String time){
        if(StrUtil.isBlankIfStr(id) || StrUtil.isBlankIfStr(time)) return R.error("参数为空");
        if(!StrUtil.isNumeric(time)) return R.error("非法参数");
        if(userTimeService.addTime(id,Integer.parseInt(time))) return R.OK();
        else return R.error("添加计时失败");
    }

    @GetMapping("/timer/lastXWeek/{x}")
    public R lastXWeek(@PathVariable(name = "x")String x){
        if(!StrUtil.isNumeric(x)) return R.error("非法参数!");
        return R.OK(userTimeService.getTimeRank(Integer.parseInt(x)));
    }

    //心跳包
    @GetMapping("/heart")
    public R heartPackage(){
        return R.OK();
    }

    @GetMapping("/timer/getNotice")
    public R getNotice(){
        return R.OK(noticeService.getCurrentNotice());
    }

}
