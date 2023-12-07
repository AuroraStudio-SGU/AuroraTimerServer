package com.aurora.day.auroratimerservernative.controller;

import com.aurora.day.auroratimerservernative.pojo.Term;
import com.aurora.day.auroratimerservernative.pojo.User;
import com.aurora.day.auroratimerservernative.schemes.R;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.schemes.request.UpLoadNoticeRequest;
import com.aurora.day.auroratimerservernative.schemes.request.setDutyRequest;
import com.aurora.day.auroratimerservernative.schemes.request.updateTermRequest;
import com.aurora.day.auroratimerservernative.service.*;
import lombok.RequiredArgsConstructor;
import org.noear.snack.ONode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
public class AdminController {

    private final INoticeService noticeService;
    private final IUserService userService;
    private final IDutyService dutyService;
    private final IUserTimeService userTimeService;
    private final ITermService termService;

    @PostMapping("/admin/uploadNotice")
    public R notice(@Valid @RequestBody UpLoadNoticeRequest request) {
        if (noticeService.insertNotice(request.toNotice())) return R.OK();
        else return R.error(ResponseState.DateBaseError);
    }

    @PostMapping("/admin/modifyNotice")
    public R modifyNotice(@Valid @RequestBody UpLoadNoticeRequest request) {
        if (request.getNotice_id() == null) return R.error(ResponseState.IllegalArgument.replaceMsg("公告id为空"));
        if (noticeService.updateNotice(request.toNotice(false))) return R.OK();
        else return R.error(ResponseState.DateBaseError);
    }


    @GetMapping("/admin/queryAllUser")
    public R getUserList(@RequestParam(value = "withAFK", defaultValue = "false") boolean withAFK) {
        return R.OK(userService.getUserList(withAFK));
    }

    @PostMapping("/admin/setDuty")
    public R setDuty(@Valid @RequestBody setDutyRequest request) {
        return R.auto(dutyService.pushDutyList(request.getWed(), request.getSun()));
    }


    @GetMapping("/admin/setUserReduceTime/{uid}/{time}")
    public R setUserReduceTime(@PathVariable("uid") String uid, @PathVariable("time") long time) {
        return R.auto(userService.setUserReduceTimeById(uid, time));
    }

    @GetMapping("/admin/setTargetTime/{hours}")
    public R setTargetTime(@PathVariable("hours") Float hours) {
        return R.auto(userTimeService.setTargetTime(hours));
    }

    @GetMapping("/admin/test")
    public R check_ok() {
        return R.OK();
    }

    @GetMapping("/admin/syncTimeFromOldData/{id}")
    public R syncDate(@RequestParam(value = "start", required = false) String start,
                      @RequestParam(value = "end", required = false) String end,
                      @PathVariable(value = "id",required = false) String id
    ) {
        return R.error(ResponseState.ERROR.replaceMsg("该功能仍需维护测试"));
//        if(id==null){
//            return R.error(ResponseState.IllegalArgument.replaceMsg("生产环境还是带上id把"));
//        }
//        return R.auto(userTimeService.transferOldTime(start, end,id));
    }


    @GetMapping("/admin/queryCurrentTerm")
    public R queryAllTerm() {
        return R.OK(termService.getCurrentTerm());
    }

    @PostMapping("/admin/updateTerm")
    public R updateTerm(@Valid updateTermRequest request) {
        return R.auto(termService.updateTermById(request.toTerm()));
    }
    @GetMapping("/getTerm")
    public R getTerm(){
        Term currentTerm = termService.getCurrentTerm();
        return R.OK(currentTerm);
    }

}
