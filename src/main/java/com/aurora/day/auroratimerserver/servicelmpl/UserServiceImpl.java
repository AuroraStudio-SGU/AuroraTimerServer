package com.aurora.day.auroratimerserver.servicelmpl;

import cn.hutool.core.util.StrUtil;
import com.aurora.day.auroratimerserver.exceptions.UserServicesException;
import com.aurora.day.auroratimerserver.mapper.UserMapper;
import com.aurora.day.auroratimerserver.mapper.UserTimeMapper;
import com.aurora.day.auroratimerserver.pojo.User;
import com.aurora.day.auroratimerserver.pojo.UserTime;
import com.aurora.day.auroratimerserver.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final UserTimeMapper userTimeMapper;

    @Override
    public User registerUser(String id, String password, String name) {
        User temp = userMapper.selectById(id);
        if (temp != null) throw new UserServicesException("该学号已被注册");
        User user = new User(id, name, password);
        if (userMapper.insert(user) != 1) throw new UserServicesException("插入数据库失败");
        return user;
    }

    @Override
    public User loginUser(String id, String password) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq(id != null, "id", id)
                .eq(password != null, "password", password);
        return userMapper.selectOne(userWrapper);//TODO 要假设会出现多条数据
    }

    @Override
    public boolean updateUser(User user) {
        User temp = userMapper.selectById(user.getId());
        if (temp == null) throw new UserServicesException("用户不存在");
        user.setAdmin(temp.isAdmin()); //布尔值更新需要先获取原来的布尔值
        return userMapper.updateById(user) == 1;
    }

    @Override
    public List<User> getUserList(boolean needAfk) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(needAfk, "afk", false);
        wrapper.select(User.class, i -> !i.getColumn().equals("password"));
        return userMapper.selectList(wrapper);
    }

    @Override
    public User queryUserById(String id) {
        return userMapper.selectOne(new QueryWrapper<User>().eq(!StrUtil.isBlankIfStr(id), "id", id));
    }

    @Override
    public boolean setUserReduceTimeById(String id, long time) {
        User user = userMapper.selectById(id);
        if (user == null) throw new UserServicesException("用户不存在");
        user.setReduceTime(time);
        return userMapper.updateById(user) == 1;
    }

    @Transactional
    @Override
    public boolean deleteUser(User user) {
        try {
            userMapper.deleteById(user);
            QueryWrapper<UserTime> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user.getId());
            userTimeMapper.delete(queryWrapper);
            return true;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
