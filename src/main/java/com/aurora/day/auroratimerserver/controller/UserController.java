package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.pojo.User;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.eums.ResponseState;
import com.aurora.day.auroratimerserver.schemes.request.LoginRequest;
import com.aurora.day.auroratimerserver.schemes.request.RegisterRequest;
import com.aurora.day.auroratimerserver.schemes.request.updateUserRequest;
import com.aurora.day.auroratimerserver.service.IUserService;
import com.aurora.day.auroratimerserver.service.IUserTimeService;
import com.aurora.day.auroratimerserver.utils.TokenUtil;
import com.aurora.day.auroratimerserver.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "UserController", description = "用户接口")
public class UserController {

    private static final Log logger = LogFactory.get();

    private final IUserService userService;
    private final IUserTimeService userTimeService;
    @Operation(summary = "注册用户")
    @PostMapping("/user/register")
    public R register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getId(), SecureUtil.md5(request.getPassword()), request.getName());
        return R.OK(user.toVo());
    }

    @Operation(summary = "登录")
    @PostMapping("/user/login")
    public R login(@Valid @RequestBody LoginRequest request) {
        User user = userService.loginUser(request.getId(), request.getPassword());
        if (user == null) return R.error(ResponseState.IllegalArgument, "账号或密码错误");
        UserVo vo = BeanUtil.toBean(user, UserVo.class);
        Long time = userTimeService.getUserWeekTimeById(vo.getId());
        if (time == null) time = 0L;
        vo.setCurrentWeekTime(time);
        vo.setToken(TokenUtil.createToken(user.getId(), user.isAdmin()));
        return R.OK(vo);
    }

    @Operation(summary = "更新用户对象")
    @PostMapping("/user/update")
    public R updateUser(@Valid @RequestBody updateUserRequest request) {
        User user = BeanUtil.toBean(request, User.class);
        user.setWorkGroup(request.getWork_group());
        if (userService.updateUser(user)) return R.OK();
        else return R.error(ResponseState.DateBaseError, ResponseState.DateBaseError.appendGet("数据库更新失败"));
    }

    @Operation(summary = "修改用户头像",parameters = {
            @Parameter(name = "file",description = "formdata(blob)头像",required = true),
            @Parameter(name = "id",description = "用户id",in= ParameterIn.PATH,required = true)
    })
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
            return R.error("上传图片失败,IO写入失败", e, true);
        }
        String linkUrl = "http://" + TimerConfig.publicHost + avatarPath.replaceAll("\\\\", "/");
        if (userService.updateUser(new User(id, null, null, linkUrl, false, false, 0, 0, null, null, null)))
            return R.OK(linkUrl);
        else return R.error(ResponseState.DateBaseError, ResponseState.DateBaseError.appendGet("数据库更新失败"));
    }

    @Operation(summary = "创建新Token",parameters = {
            @Parameter(name = "id",description = "用户id",in=ParameterIn.PATH,required = true)
    })
    @GetMapping("/user/newToken/{id}")
    public R generateToken(@PathVariable("id") String id) {
        User user = userService.queryUserById(id);
        if (user == null) return R.error(ResponseState.IllegalArgument, "用户不存在");
        return R.OK(TokenUtil.createToken(user.getId(), user.isAdmin()));
    }

    @Operation(summary = "根据token登录",parameters = {
            @Parameter(name = "token",description = "token",in=ParameterIn.PATH,required = true)
    })
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

    @Operation(summary = "重置密码",parameters = {
            @Parameter(name = "id",description = "用户id",in = ParameterIn.PATH,required = true),
            @Parameter(name = "pwd",description = "新密码",in = ParameterIn.QUERY,required = true)
    })
    @GetMapping("/user/resetPassword/{id}")
    public R resetPwd(@PathVariable("id") String id, @RequestParam("pwd") String pwd) {
        User user = userService.queryUserById(id);
        if (user == null) return R.error(ResponseState.IllegalArgument, "用户不存在");
        user.setPassword(SecureUtil.md5(pwd));
        userService.updateUser(user);
        return R.OK("重置成功，新密码为" + pwd);
    }
    @Operation(summary = "获取用户头像",parameters = {
            @Parameter(name = "id",description = "用户id",in=ParameterIn.PATH,required = true)
    })
    @GetMapping("/user/avatar/{id}")
    public R getAvatar(@PathVariable("id") String id) {
        User user = userService.queryUserById(id);
        if (user == null) return R.error(ResponseState.IllegalArgument, "用户不存在");
        if (user.getAvatar() == null) return R.OK(TimerConfig.avatarDefaultUrl);
        else return R.OK(user.getAvatar());
    }
    @Operation(summary = "查询用户",parameters = {
            @Parameter(name = "id",description = "用户id",in=ParameterIn.PATH,required = true)
    })
    @GetMapping("/user/{id}")
    public R queryUser(@PathVariable("id") String id) {
        User user = userService.queryUserById(id);
        return R.OK(user.toVo());
    }
    @Operation(summary = "获取默认头像")
    @GetMapping("/getDefaultAvatar")
    public R defaultAvatar() {
        return R.OK(TimerConfig.avatarDefaultUrl);
    }

}
