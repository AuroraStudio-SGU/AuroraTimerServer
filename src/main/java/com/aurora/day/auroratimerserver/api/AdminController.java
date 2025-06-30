package com.aurora.day.auroratimerserver.api;

import com.aurora.day.auroratimerserver.annotation.AdminRequired;
import com.aurora.day.auroratimerserver.model.Result;
import com.aurora.day.auroratimerserver.schame.req.SetDutyReq;
import com.aurora.day.auroratimerserver.services.IDutyService;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Post;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.validation.annotation.Validated;

@Controller
@AdminRequired
@Mapping("admin")
@Valid
public class AdminController {

    @Inject
    IDutyService dutyService;

    @Get
    @Mapping
    public String pingAsAdmin(){
        return "pong as Admin";
    }

    /**
     * 设置本周的值日人员
     */
    @Post
    @Mapping("setDuty")
    public Result<String> setDutyList(
            @Validated SetDutyReq req
    ) {
        return Result.success(dutyService.InsertDutySchedule(req));
    }


}
