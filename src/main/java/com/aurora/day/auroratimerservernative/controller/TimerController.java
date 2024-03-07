package com.aurora.day.auroratimerservernative.controller;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerservernative.cache.RankCache;
import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.exceptions.TimeServicesException;
import com.aurora.day.auroratimerservernative.pojo.Term;
import com.aurora.day.auroratimerservernative.pojo.UserTime;
import com.aurora.day.auroratimerservernative.pojo.WeeklyDustList;
import com.aurora.day.auroratimerservernative.schemes.R;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.schemes.response.ImageData;
import com.aurora.day.auroratimerservernative.schemes.response.UploadImageResponse;
import com.aurora.day.auroratimerservernative.schemes.response.UserTimeDetailResponse;
import com.aurora.day.auroratimerservernative.service.IDutyService;
import com.aurora.day.auroratimerservernative.service.INoticeService;
import com.aurora.day.auroratimerservernative.service.ITermService;
import com.aurora.day.auroratimerservernative.service.IUserTimeService;
import com.aurora.day.auroratimerservernative.pojo.UserOnlineTime;
import lombok.RequiredArgsConstructor;
import org.noear.snack.ONode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor

public class TimerController {

    private final Log logger = LogFactory.get(TimerController.class);
    private final IUserTimeService userTimeService;

    private final INoticeService noticeService;
    private final IDutyService dutyService;
    private final ITermService termService;

    //版本黑名单,有严重bug的版本提醒更换
    private static final List<String> BlackListVersion = Arrays.asList("0.0.8");


    //添加计时时长
    @GetMapping("/timer/addTime/{id}")
    public R addTime(@PathVariable(name = "id") String id,@RequestParam(value = "version",required = false)String version) {
        if (StrUtil.isBlankIfStr(id)) return R.error(ResponseState.IllegalArgument.replaceMsg("参数为空"));
        try {
            userTimeService.addTime(id, 60);
            if(version!=null && BlackListVersion.contains(version)){
                return R.error(ResponseState.BlackListVersion,ResponseState.BlackListVersion.getMsg());
            }
            return R.OK(userTimeService.getUserWeekTimeById(id));
        } catch (TimeServicesException e) {
            logger.warn("数据添加计时数据失败");
            return R.error(ResponseState.ERROR, "添加计时失败");
        }
    }
    //排行榜缓存
    private static final TimedCache<String, List<UserOnlineTime>> RankCache = CacheUtil.newTimedCache(DateUnit.MINUTE.getMillis());

    //查询某周的打卡情况。
    @GetMapping("/timer/lastXWeek/{x}")
    public R lastXWeek(@PathVariable(name = "x") String x) {
        if (!StrUtil.isNumeric(x)) return R.error(ResponseState.IllegalArgument, "非法参数!");
        List<UserOnlineTime> cache = RankCache.get(x, false);
        if(cache==null){
            cache = userTimeService.getTimeRank(Integer.parseInt(x));
            RankCache.put(x,cache);
        }
        return R.OK(cache);
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
            long currentTargetTime = (long) userTimeService.getTargetTime().getTargetTime() * 3600L;
            List<UserOnlineTime> list = userTimeService.getTimeRank(1);
            List<UserOnlineTime> LastRank = new ArrayList<>(3);
            for (int i = list.size() - 1; i >= 0; i--) {
                UserOnlineTime userTime = list.get(i);
                long time = userTime.getWeekTime() + userTime.getReduceTime();
                if (Math.abs(time) < currentTargetTime) {
                    LastRank.add(userTime);
                    if (LastRank.size() == 3) break;
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
        JSONObject json = new JSONObject();
        json.set("pong","pong");
        json.set("version","0.0.3");
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
            return R.error(ResponseState.DateBaseError, "没有值日表");
        }
        return R.OK(dust);
    }

    @GetMapping("/getTargetTime")
    public R getTargetTime() {
        return R.OK(userTimeService.getTargetTime());
    }


    @GetMapping("/timer/getWeekTime/{id}")
    public R getWeekTime(@PathVariable("id") String id) {
        Long time = userTimeService.getUserWeekTimeById(id);
        if (time == null) time = 0L;
        return R.OK(time);
    }


    @GetMapping("/timer/queryTimeDetail/{id}")
    public R getUserTimeDetail(@PathVariable("id") String id) {
        Term term = termService.getCurrentTerm();
        List<UserTime> userTimes = userTimeService.queryTime(id,
                DateUtil.format(term.start, DatePattern.NORM_DATE_PATTERN),
                DateUtil.format(term.end, DatePattern.NORM_DATE_PATTERN)
        );
        UserTimeDetailResponse res = new UserTimeDetailResponse();
        List<String> dateList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        userTimes.forEach(t -> {
            dateList.add(DateUtil.format(t.getRecordDate(), DatePattern.NORM_DATE_PATTERN));
            timeList.add(NumberUtil.decimalFormat("#.###",t.getOnlineTime() / 3600.0));
        });
        res.setDates(dateList);
        res.setTimes(timeList);
        return R.OK(res);
    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/uploadImage")
    public UploadImageResponse uploadImage(@RequestPart("file")MultipartFile file){
        UploadImageResponse response = new UploadImageResponse();
        try{
            File folder = new File(TimerConfig.filePath,"resources");//专门放杂七杂八的图片
            if(!folder.exists()){
                if(folder.isDirectory() && folder.mkdirs()){
                    throw new RuntimeException("Create folder fail");
                }
            }
            //检查下文件存放数量是否太多，进行清理
            if(folder.isDirectory()){
                File[] files = folder.listFiles();
                //懒得写config了
                if(files!=null && files.length>=20){
                    Arrays.sort(files,(o1, o2) -> Math.toIntExact(o2.lastModified() - o1.lastModified()));
                    for (int i = 0; i < 10; i++) {
                        files[i].deleteOnExit();
                    }
                }
            }
            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File imageFile = FileUtil.file(folder,fileName);
            FileUtil.writeBytes(file.getBytes(),imageFile);
            String linkUrl = "http://" + TimerConfig.publicHost + "/" + "resources/" + fileName;
            response.setData(new ImageData(linkUrl,file.getOriginalFilename()));
            return response;
        }catch (Throwable t){
            logger.warn("上传文件失败:",t);
            response.setErrno(1);
            response.setMessage(t.getCause().getLocalizedMessage()==null?"空指针异常":t.getCause().getLocalizedMessage());
            return response;
        }
    }
    private static String MINI_VERSION = "0.0.12";
    @GetMapping("/getMiniVersion")
    public R getMiniVersion(){
        return R.OK(MINI_VERSION);
    }
    @GetMapping("/setMiniVersion/{version}")
    public R setMiniVersion(@PathVariable("version")String version){
        MINI_VERSION = version;
        return R.OK();
    }

}
