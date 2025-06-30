package com.aurora.day.auroratimerserver.api;

import com.aurora.day.auroratimerserver.model.Result;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Param;
import org.noear.solon.annotation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Mapping("timer")
public class TimerController {
    static Logger log = LoggerFactory.getLogger(TimerController.class);

    @Get
    @Mapping("addTime/{id}")
    public Result addTime(@Path String id,@Param(value = "version",required = false)String version){

        return Result.success(null);
    }
}
