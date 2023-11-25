package com.aurora.day.auroratimerservernative.task;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerservernative.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//TODO TASK
/**
 * task mission
 * 尝试清理过时数据
 * 重置自公告、减时、值日等系统。
 */
@Component
@RequiredArgsConstructor
public class WeeklyTask {

    private final UserMapper userMapper;
    private static final Log logger = LogFactory.get(WeeklyTask.class);

    @Scheduled(cron = "0 0 19 * * 1 *")
    public void resetReduceTime() {
        try{
            int count = userMapper.resetAllReduceTime();
            logger.info("重置减时人数:{}",count);
        }catch (Throwable t){
            logger.warn("重置减时失败");
        }
    }

}
