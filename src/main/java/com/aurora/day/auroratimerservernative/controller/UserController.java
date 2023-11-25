package com.aurora.day.auroratimerservernative.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.pojo.User;
import com.aurora.day.auroratimerservernative.schemes.R;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.schemes.request.LoginRequest;
import com.aurora.day.auroratimerservernative.schemes.request.RegisterRequest;
import com.aurora.day.auroratimerservernative.schemes.request.updateUserRequest;
import com.aurora.day.auroratimerservernative.service.IUserService;
import com.aurora.day.auroratimerservernative.service.IUserTimeService;
import com.aurora.day.auroratimerservernative.utils.TokenUtil;
import com.aurora.day.auroratimerservernative.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
public class UserController {

    private static final Log logger = LogFactory.get();

    private final IUserService userService;
    private final IUserTimeService userTimeService;

    @PostMapping("/user/register")
    public R register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.toUser());
        return R.OK(user.toVo());
    }


    @PostMapping("/user/login")
    public R login(@Valid @RequestBody LoginRequest request) {
        User user = userService.loginUser(request.getId(), request.getPassword());
        if (user == null) return R.error(ResponseState.IllegalArgument.replaceMsg("账号或密码错误"));
        UserVo vo = BeanUtil.toBean(user, UserVo.class);
        Long time = userTimeService.getUserWeekTimeById(vo.getId());
        if (time == null) time = 0L;
        vo.setCurrentWeekTime(time);
        vo.setToken(TokenUtil.createToken(user.getId(), user.isAdmin()));
        return R.OK(vo);
    }


    @PostMapping("/user/update")
    public R updateUser(@Valid @RequestBody updateUserRequest request) {
        User user = BeanUtil.toBean(request, User.class);
        user.setWorkGroup(request.getWork_group());
        if (userService.updateUser(user)) return R.OK();
        else return R.error(ResponseState.DateBaseError, ResponseState.DateBaseError.appendMsg("数据库更新失败"));
    }


    @PostMapping("/user/uploadAvatar/{id}")
    public R avatar(@RequestParam("file") MultipartFile file, @PathVariable(name = "id") String id) {
        if (file == null) return R.error(ResponseState.IllegalArgument, "图片数据为空");
        String fileName = "Avatar-" + id + ".png";
        String avatarPath = File.separator + "avatars" + File.separator + fileName;
        //若之前存在过头像则直接覆写
        File avatar = FileUtil.file(TimerConfig.filePath + avatarPath);
        try {
            FileUtil.writeBytes(file.getBytes(), avatar);
        } catch (Throwable e) {
            logger.error("控制层IO异常{}", e.getLocalizedMessage());
            return R.error(ResponseState.ERROR.replaceMsg("上传图片失败,IO写入失败"), e, true);
        }
        String linkUrl = "http://" + TimerConfig.publicHost + avatarPath.replaceAll("\\\\", "/");
        if (userService.updateUser(new User(id, null, null, linkUrl, false, false, 0, 0, null, null, null)))
            return R.OK(linkUrl);
        else return R.error(ResponseState.DateBaseError, ResponseState.DateBaseError.appendMsg("数据库更新失败"));
    }


    @GetMapping("/user/newToken/{id}")
    public R generateToken(@PathVariable("id") String id) {
        User user = userService.queryUserById(id);
        if (user == null) return R.error(ResponseState.IllegalArgument, "用户不存在");
        return R.OK(TokenUtil.createToken(user.getId(), user.isAdmin()));
    }


    @GetMapping("/user/loginByToken/{token}")
    public R loginWithToken(@PathVariable("token") String token) {
        String uid = TokenUtil.getId(token);
        if (uid != null) {
            User user = userService.queryUserById(uid);
            Long time = userTimeService.getUserWeekTimeById(uid);
            if (time == null) time = 0L;
            UserVo vo = BeanUtil.toBean(user, UserVo.class);
            vo.setCurrentWeekTime(time);
            vo.setToken(TokenUtil.createToken(user.getId(), user.isAdmin()));
            return R.OK(vo);
        } else {
            return R.error(ResponseState.AuthorizationError, "token不正确或token已过期");
        }
    }


    @GetMapping("/user/resetPassword/{id}")
    public R resetPwd(@PathVariable("id") String id, @RequestParam("pwd") String pwd) {
        User user = userService.queryUserById(id);
        if (user == null) return R.error(ResponseState.IllegalArgument, "用户不存在");
        user.setPassword(SecureUtil.md5(pwd));
        userService.updateUser(user);
        return R.OK("重置成功，新密码为" + pwd);
    }

    @GetMapping("/user/avatar/{id}")
    public R getAvatar(@PathVariable("id") String id) {
        User user = userService.queryUserById(id);
        if (user == null) return R.error(ResponseState.IllegalArgument, "用户不存在");
        if (user.getAvatar() == null) return R.OK(TimerConfig.avatarDefaultUrl);
        else return R.OK(user.getAvatar());
    }

    @GetMapping("/user/{id}")
    public R queryUser(@PathVariable("id") String id) {
        User user = userService.queryUserById(id);
        return R.OK(user.toVo());
    }
    @GetMapping("/getDefaultAvatar")
    public R defaultAvatar() {
        return R.OK(TimerConfig.avatarDefaultUrl);
    }

}
