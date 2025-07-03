package com.aurora.day.auroratimerserver.services.impl;

import com.aurora.day.auroratimerserver.mapper.UserMapper;
import com.aurora.day.auroratimerserver.model.User;
import com.aurora.day.auroratimerserver.model.vo.UserVo;
import com.aurora.day.auroratimerserver.services.IUserService;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;

@Component
public class UserService implements IUserService {

    @Inject
    UserMapper userMapper;

    @Override
    public UserVo queryUserVo(String id) {
        return userMapper.selectOneWithRelationsByIdAs(id, UserVo.class);
    }

    @Override
    public User queryUser(String id) {
        return userMapper.selectOneById(id);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.update(user, true) > 0;
    }

    @Override
    public boolean deleteUser(String id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean registerUser(User user) {
        return userMapper.insert(user) == 1;
    }

    @Override
    public boolean isExitUser(String id) {
        return queryUser(id) != null;
    }

    @Override
    public List<UserVo> queryAllUsers(boolean withAfk) {
        QueryWrapper wp = new QueryWrapper();
        wp.eq(User::getAfk, true, withAfk);
        return userMapper.selectListByQueryAs(wp, UserVo.class);
    }
}
