package com.aurora.day.auroratimerserver.controller;

import com.aurora.day.auroratimerserver.pojo.User;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.eums.ResponseState;
import com.aurora.day.auroratimerserver.schemes.request.UpLoadNoticeRequest;
import com.aurora.day.auroratimerserver.schemes.request.setDutyRequest;
import com.aurora.day.auroratimerserver.schemes.request.updateTermRequest;
import com.aurora.day.auroratimerserver.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    @Operation(summary = "上传公告")
    @PostMapping("/admin/uploadNotice")
    public R notice(@Valid @RequestBody UpLoadNoticeRequest request) {
        if (noticeService.insertNotice(request.toNotice())) return R.OK();
        else return R.error("上传失败");
    }

    @Operation(summary = "修改公告(需要传入notice_id)")
    @PostMapping("/admin/modifyNotice")
    public R modifyNotice(@Valid @RequestBody UpLoadNoticeRequest request) {
        if (request.getNotice_id() == null) return R.error(ResponseState.IllegalArgument, "公告id为空");
        if (noticeService.updateNotice(request.toNotice(false))) return R.OK();
        else return R.error("更新失败");
    }

    @Operation(summary = "查询所有用户", parameters = {
            @Parameter(name = "withAFK", description = "是否算上已退休的(默认为否)", in = ParameterIn.PATH)
    })
    @GetMapping("/admin/queryAllUser")
    public R getUserList(@RequestParam(value = "withAFK", defaultValue = "false") boolean withAFK) {
        return R.OK(userService.getUserList(withAFK));
    }

    @Operation(summary = "设置值日表")
    @PostMapping("/admin/setDuty")
    public R setDuty(@Valid @RequestBody setDutyRequest request) {
        return R.auto(dutyService.pushDutyList(request.getWed(), request.getSun()));
    }

    @Operation(summary = "设置减时情况",
            parameters = {
                    @Parameter(name = "uid", description = "用户id",required = true),
                    @Parameter(name = "time", description = "减时时长,单位秒",required = true)
            }
    )
    @GetMapping("/admin/setUserReduceTime/{uid}/{time}")
    public R setUserReduceTime(@PathVariable("uid") String uid, @PathVariable("time") long time) {
        return R.auto(userService.setUserReduceTimeById(uid, time));
    }
    @Operation(summary = "设置目标计时时长",
            parameters = {
                    @Parameter(name = "hours", description = "时长,单位小时",required = true),
            }
    )
    @GetMapping("/admin/setTargetTime/{hours}")
    public R setTargetTime(@PathVariable("hours") Float hours) {
        return R.auto(userTimeService.setTargetTime(hours));
    }

    @Operation(summary = "测试权限是否正常")
    @GetMapping("/admin/test")
    public R check_ok() {
        return R.OK();
    }
    @Operation(summary = "从旧计时器同步计时情况到新的",
            parameters = {
                    @Parameter(name = "start", description = "起始时间(为空则为本学期)"),
                    @Parameter(name = "end", description = "结尾时间(为空则为本学期)")
            }
    )
    @GetMapping("/admin/syncTimeFromOldData")
    public R syncDate(@RequestParam(value = "start", required = false) String start, @RequestParam(value = "end", required = false) String end) {
        return R.OK(userTimeService.transferOldTime(start, end));
    }

    @Operation(summary = "查询所有储存的学期")
    @GetMapping("/admin/queryAllTerm")
    public R queryAllTerm() {
        return R.OK(termService.getAllTerms());
    }
    @Operation(summary = "设置减时情况")
    @PostMapping("/admin/updateTerm")
    public R updateTerm(@Valid updateTermRequest request) {
        return R.auto(termService.updateTermById(request.toTerm()));
    }

    @Operation(summary = "删除用户(真删)",parameters = {
            @Parameter(name = "id",description = "用户id",required = true)
    })
    @GetMapping("/admin/deleteUser/{id}")
    public R deleteUser(@PathVariable("id") String id) {
        User user = userService.queryUserById(id);
        if (user == null) return R.error(ResponseState.IllegalArgument, "用户不存在");
        return R.auto(userService.deleteUser(user));
    }

}
