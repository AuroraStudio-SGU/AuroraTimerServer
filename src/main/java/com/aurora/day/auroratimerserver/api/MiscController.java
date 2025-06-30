package com.aurora.day.auroratimerserver.api;

import com.aurora.day.auroratimerserver.model.DutySchedule;
import com.aurora.day.auroratimerserver.model.Result;
import com.aurora.day.auroratimerserver.model.Term;
import com.aurora.day.auroratimerserver.services.IDutyService;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;

import java.util.List;

/**
 * 一些杂项接口
 */
@Controller
public class MiscController {

    @Inject
    IDutyService dutyService;

    @Get
    @Mapping("ping")
    public String ping() {
        return "pong";
    }

    @Get
    @Mapping("getDustList")
    public Result<DutySchedule> getDustList() {
        return Result.success(dutyService.getCurrentWeekSchedule());
    }

    /**
     * 查询所有学期信息
     */
    @Get
    @Mapping("terms")
    public Result<List<Term>> queryAllTerms(){
        //TODO
        return Result.success(null);
    }

    /**
     * 获取当前学期信息
     */
    @Get
    @Mapping("term")
    public Result<Term> queryCurrentTerm(){
        //TODO
        return Result.success(null);
    }


}
