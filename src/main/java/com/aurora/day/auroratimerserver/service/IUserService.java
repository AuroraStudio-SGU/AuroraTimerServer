package com.aurora.day.auroratimerserver.service;

import com.aurora.day.auroratimerserver.pojo.User;

public interface IUserService {

    /**
     * 注册用户
     * @param id 学号
     * @param password 密码
     * @param name 姓名
     * @return 若注册成功则返回注册后用户信息
     */
    User registerUser(String id,String password,String name);
    /**
     * 用户登录
     * @param id 学号
     * @param password 密码
     * @return 若登录成功则返回注册后用户信息
     */
    User loginUser(String id,String password);
    /**
     * 更新用户
     * @param user 需要更新的用户实体
     * @return 是否更新成功
     */
    boolean updateUser(User user);
}
