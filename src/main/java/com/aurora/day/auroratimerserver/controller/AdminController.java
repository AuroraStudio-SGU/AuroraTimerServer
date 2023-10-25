package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerserver.pojo.Term;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.request.UpLoadNoticeRequest;
import com.aurora.day.auroratimerserver.schemes.request.setDutyRequest;
import com.aurora.day.auroratimerserver.schemes.request.updateTermRequest;
import com.aurora.day.auroratimerserver.service.*;
import com.aurora.day.auroratimerserver.servicelmpl.NoticeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
@Tag(name = "AdminController", description = "管理员操作接口")
public class AdminController {

    private final INoticeService noticeService;
    private final IUserService userService;
    private final IDutyService dutyService;
    private final IUserTimeService userTimeService;
    private final ITermService termService;

    @PostMapping("/admin/uploadNotice")
    public R notice(@Valid @RequestBody UpLoadNoticeRequest request){
        if(noticeService.insertNotice(request.toNotice())) return R.OK();
        else return R.error("上传失败");
    }
    @PostMapping("/admin/modifyNotice")
    public R modifyNotice(@Valid @RequestBody UpLoadNoticeRequest request){
        if(noticeService.updateNotice(request.toNotice(false))) return R.OK();
        else return R.error("更新失败");
    }
    @GetMapping("/admin/queryAllUser")
    public R getUserList(@RequestParam(value = "withAFK",defaultValue = "false")boolean withAFK){
        return R.OK(userService.getUserList(withAFK));
    }

    @PostMapping("/admin/setDuty")
    public R setDuty(@Valid @RequestBody setDutyRequest request){
        return R.auto(dutyService.pushDutyList(request.getWed(),request.getSun()));
    }

    @GetMapping("/admin/setUserReduceTime/{uid}/{time}")
    public R setUserReduceTime(@PathVariable("uid")String uid,@PathVariable("time")long time){
        return R.auto(userService.setUserReduceTimeById(uid, time));
    }

    @GetMapping("/admin/setTargetTime/{hours}")
    public R setTargetTime(@PathVariable("hours")Float hours){
        return R.auto(userTimeService.setTargetTime(hours));
    }

    @GetMapping("/admin/test")
    public R check_ok(){
        return R.OK();
    }

    @GetMapping("/admin/syncTimeFromOldData")
    public R syncDate(@RequestParam(value = "start",required = false)String start,@RequestParam(value = "end",required = false)String end){
        return R.OK(userTimeService.transferOldTime(start,end));
    }

    @GetMapping("/admin/queryAllTerm")
    public R queryAllTerm(){
        return R.OK(termService.getAllTerms());
    }
    @PostMapping("/admin/updateTerm")
    public R updateTerm(@Valid updateTermRequest request){
        return R.auto(termService.updateTermById(request.toTerm()));
    }


}
