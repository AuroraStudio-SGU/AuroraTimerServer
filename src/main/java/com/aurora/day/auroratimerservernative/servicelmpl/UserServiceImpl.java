package com.aurora.day.auroratimerservernative.servicelmpl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerservernative.exceptions.UserServicesException;
import com.aurora.day.auroratimerservernative.mapper.UserMapper;
import com.aurora.day.auroratimerservernative.mapper.UserTimeMapper;
import com.aurora.day.auroratimerservernative.pojo.User;
import com.aurora.day.auroratimerservernative.pojo.UserTime;
import com.aurora.day.auroratimerservernative.schemes.eums.PrivilegeEnum;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private static final Log logger = LogFactory.get("UserService");

    private final UserMapper userMapper;
    private final UserTimeMapper userTimeMapper;

    @Override
    public User registerUser(User newUser) {
        User temp = userMapper.selectById(newUser.getId());
        if (temp != null) throw new UserServicesException(ResponseState.IllegalArgument.replaceMsg("该学号已被注册"));
        if (userMapper.insert(newUser) != 1)
            throw new UserServicesException(ResponseState.DateBaseError.replaceMsg("插入数据库失败"));
        return newUser;
    }

    @Override
    public User loginUser(String id, String password) {
        try {
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq(id != null, "id", id)
                    .eq(password != null, "password", password);
            return userMapper.selectOne(userWrapper);//TODO 要假设会出现多条数据
        } catch (Exception e) {
            logger.warn("多用户查询,用户id:" + id);
            throw e;
        }
    }

    @Override
    public boolean updateUser(User user) {
        User temp = userMapper.selectById(user.getId());
        if (temp == null) throw new UserServicesException(ResponseState.IllegalArgument.replaceMsg("用户不存在"));
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
        try {
            return userMapper.selectOne(new QueryWrapper<User>().eq(!StrUtil.isBlankIfStr(id), "id", id));
        } catch (Exception e) {
            logger.warn("多用户查询,用户id:" + id);
            throw e;
        }
    }

    @Override
    public boolean setUserReduceTimeById(String id, long time) {
        User user = userMapper.selectById(id);
        if (user == null) throw new UserServicesException(ResponseState.IllegalArgument.replaceMsg("用户不存在"));
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

    @Transactional
    @Override
    public boolean batchDeleteUser(List<String> ids) {
        int count = userMapper.deleteBatchIds(ids);
        try {
            QueryWrapper<UserTime> wrapper = new QueryWrapper<>();
            wrapper.in("user_id",ids);
            userTimeMapper.delete(wrapper);
        } catch (Throwable e) {
            logger.warn(e);
            throw new UserServicesException(ResponseState.DateBaseError.replaceMsg("数据库异常"));
        }
        if (count != ids.size()) {
            throw new UserServicesException(ResponseState.DateBaseError.replaceMsg("数据库异常"));
        } else {
            return true;
        }
    }

    @Override
    public PrivilegeEnum queryPriv(String id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("priv");
        wrapper.eq(!id.isBlank(), "id", id);
        User temp = userMapper.selectOne(wrapper);
        return PrivilegeEnum.conventToEnum(temp.getPriv());
    }
}
