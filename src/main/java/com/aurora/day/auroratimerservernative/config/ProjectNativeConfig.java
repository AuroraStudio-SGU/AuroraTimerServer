package com.aurora.day.auroratimerservernative.config;

import com.aurora.day.auroratimerservernative.pojo.Term;
import com.aurora.day.auroratimerservernative.pojo.UserOnlineTime;
import com.aurora.day.auroratimerservernative.pojo.UserVo;
import com.aurora.day.auroratimerservernative.schemes.R;
import com.aurora.day.auroratimerservernative.schemes.response.UserTimeDetailResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(ProjectNativeConfig.class)
public class ProjectNativeConfig implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        //偷偷加个poi
        //需要序列化输出的对象都要在这里注册
        hints.serialization().registerType(R.class);
        hints.serialization().registerType(UserVo.class);
        hints.serialization().registerType(UserOnlineTime.class);
        hints.serialization().registerType(UserTimeDetailResponse.class);
        hints.serialization().registerType(Term.class);
        hints.reflection().registerType(HSSFWorkbook.class);
    }
}
