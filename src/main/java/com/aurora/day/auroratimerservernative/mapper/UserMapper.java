package com.aurora.day.auroratimerservernative.mapper;

import com.aurora.day.auroratimerservernative.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set reduce_time = 0")
    int resetAllReduceTime();
}
