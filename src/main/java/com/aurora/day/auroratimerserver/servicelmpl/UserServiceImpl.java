package com.aurora.day.auroratimerserver.servicelmpl;

import cn.hutool.core.util.StrUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.exceptions.UserServicesException;
import com.aurora.day.auroratimerserver.mapper.UserMapper;
import com.aurora.day.auroratimerserver.pojo.User;
import com.aurora.day.auroratimerserver.service.IUserService;
import com.aurora.day.auroratimerserver.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    @Override
    public User registerUser(String id, String password,String name) {
        User temp = userMapper.selectById(id);
        if(temp!=null) throw new UserServicesException("该学号已被注册");
        User user = new User(id,name,password, TimerConfig.avatarDefaultUrl, false,false,0,0,null,null,null);
        if(userMapper.insert(user)!=1) throw new UserServicesException("插入数据库失败");
        return user;
    }

    @Override
    public User loginUser(String id, String password) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq(id!=null,"id",id)
                   .eq(password!=null,"password",password);
        return userMapper.selectOne(userWrapper);//TODO 要假设会出现多条数据
    }

    @Override
    public boolean updateUser(User user) {
        User temp = userMapper.selectById(user.getId());
        if(temp==null) throw new UserServicesException("用户不存在");
        user.setAdmin(temp.isAdmin()); //布尔值更新需要先获取原来的布尔值
        return userMapper.updateById(user)==1;
    }

    @Override
    public List<UserVo> getCurrentUser(String year) {
        int targetYear = Integer.parseInt(year);
        List<UserVo> list = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("id",targetYear)
                    .or(userQueryWrapper -> userQueryWrapper.likeRight("id",targetYear-1))
                    .or(userQueryWrapper -> userQueryWrapper.likeRight("id",targetYear-2))
                    .or(userQueryWrapper -> userQueryWrapper.likeRight("id",targetYear-3));
        userMapper.selectList(queryWrapper).forEach(u->list.add(u.toVo()));

        return list;
    }

    @Override
    public User queryUserById(String id) {
        return userMapper.selectOne(new QueryWrapper<User>().eq(!StrUtil.isBlankIfStr(id),"id",id));
    }

    @Override
    public boolean setUserReduceTimeById(String id, long time) {
        User user = userMapper.selectById(id);
        if(user==null) throw new UserServicesException("用户不存在");
        user.setReduceTime(time);
        return userMapper.updateById(user) == 1;
    }
}
