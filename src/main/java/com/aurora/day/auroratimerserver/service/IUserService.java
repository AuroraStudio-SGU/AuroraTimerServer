package com.aurora.day.auroratimerserver.service;

import com.aurora.day.auroratimerserver.pojo.User;
import com.aurora.day.auroratimerserver.vo.UserVo;

import java.util.List;

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

    /***
     * 获取全部人员
     * @param needAfk 是否需要已退休的
     * @return 人员列表
     */
    List<User> getUserList(boolean needAfk);

    /***
     * ignored
     */
    User queryUserById(String id);

    /**
     * 设置用户用时时长，下周清空
     * @param id 用户id
     * @param time 需要减的时长,单位：秒
     * @return 是否成功
     */
    boolean setUserReduceTimeById(String id,long time);
}
