package com.aurora.day.auroratimerserver.services;

import com.aurora.day.auroratimerserver.model.User;
import com.aurora.day.auroratimerserver.model.vo.UserVo;

import java.util.List;

public interface IUserService {

    UserVo queryUserVo(String id);
    User queryUser(String id);

    boolean updateUser(User user);

    boolean deleteUser(String id);

    boolean registerUser(User user);

    boolean isExitUser(String id);

    List<UserVo> queryAllUsers(boolean withAfk);


}
