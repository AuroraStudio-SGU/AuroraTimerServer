package com.aurora.day.auroratimerserver.api;

import com.aurora.day.auroratimerserver.model.Result;
import com.aurora.day.auroratimerserver.model.UserTimeBuffer;
import com.aurora.day.auroratimerserver.model.vo.UserTimeReport;
import com.aurora.day.auroratimerserver.services.IUserService;
import com.aurora.day.auroratimerserver.services.IUserTimeService;
import com.aurora.day.auroratimerserver.utils.UserTimeBufferedBatchWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Path;
import org.noear.solon.validation.annotation.Max;
import org.noear.solon.validation.annotation.Min;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Mapping("timer")
public class TimerController extends BaseController {

    @Inject
    IUserTimeService userTimeService;
    @Inject
    IUserService userService;
    @Inject
    UserTimeBufferedBatchWriter bufferedBatchWriter;

    //计时器缓存
    private final LoadingCache<String, UserTimeBuffer> timerBuffer =
            Caffeine.newBuilder()
                    .expireAfterAccess(1, TimeUnit.HOURS)//1h后没命中则删除
                    //失效后从数据库查询
                    .build((key -> userTimeService.getTodayUserTimeAsBuffer(key)));

    /**
     * 向计时器请求添加时长
     * 请求成功后添加1分钟
     *
     * @param id 学号
     */
    @Get
    @Mapping("addTime/{id}")
    public Result<Long> addTime(@Path String id) {
        UserTimeBuffer time = timerBuffer.get(id);
        //理应不会出现null的情况，查询不到也会返回对象
        if (time == null) {
            time = userTimeService.getTodayUserTimeAsBuffer(id);
        }
        //TODO 判断合理误差后加时
        time.plusTime(60);
        timerBuffer.put(id, time);
        //添加到更新队列
        bufferedBatchWriter.addToBuffer(id, time);
        return Result.success(time.getWeeklyTime());
    }

    /**
     * 查看某周的打卡排名
     *
     * @param week 周偏移值 只允许查看历史第n周
     */
    @Get
    @Mapping("rank/{x}")
    public Result<List<UserTimeReport>> queryWeeklyRank(
            @Path @Min(0) @Max(100) Integer week
    ) {
        return Result.success(userTimeService.queryTimeRank(week));
    }

    /**
     * 查询某人本周的打卡时长
     *
     * @param id 学号
     */
    @Get
    @Mapping("week/{id}")
    public Result<Long> queryWeeklyRank(
            @Path String id
    ) {
        if (!userService.isExitUser(id)) {
            return Result.fail("用户不存在");
        }
        return Result.success(userTimeService.getUserWeeklyTime(id));
    }

    /**
     * 查询本周打卡时长最高的3人
     * 有一天的数据延迟
     */
    @Get
    @Mapping("top3")
    public Result<List<UserTimeReport>> getTopRank() {
        return Result.success(userTimeService.getLimitUserTimeRank(0, 3));
    }

    /**
     * 查询本周打卡时长最低的3人
     * 有一天的数据延迟
     */
    @Get
    @Mapping("las3")
    public Result<List<UserTimeReport>> getDownRank() {
        return Result.success(userTimeService.getLimitUserTimeRank(0, -3));
    }
}
