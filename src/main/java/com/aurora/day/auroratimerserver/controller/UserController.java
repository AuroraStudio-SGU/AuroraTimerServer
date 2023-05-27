package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.aurora.day.auroratimerserver.pojo.User;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.request.LoginRequest;
import com.aurora.day.auroratimerserver.schemes.request.RegisterRequest;
import com.aurora.day.auroratimerserver.schemes.request.updateUserRequest;
import com.aurora.day.auroratimerserver.servicelmpl.IUserServiceImpl;
import com.aurora.day.auroratimerserver.utils.TokenUtil;
import com.aurora.day.auroratimerserver.vo.UserVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
@Tag(name = "UserController", description = "用户接口")
public class UserController {

    private final IUserServiceImpl userService;

    @PostMapping("/user/register")
    public R register(@Valid @RequestBody RegisterRequest request){
        User user = userService.registerUser(request.getId(), SecureUtil.md5(request.getPassword()),request.getName());
        return R.OK(BeanUtil.toBean(user, UserVo.class));
    }

    @PostMapping("/user/login")
    public R login(@Valid @RequestBody LoginRequest request){
        User user = userService.loginUser(request.getId(),SecureUtil.md5(request.getPassword()));
        UserVo vo = BeanUtil.toBean(user, UserVo.class);
        vo.setToken(TokenUtil.createToken(user.getId(),user.isAdmin()));
        return R.OK(vo);
    }

    @PostMapping("/user/update")
    public R updateUser(@Valid @RequestBody updateUserRequest request){
        if(userService.updateUser(BeanUtil.toBean(request, User.class))) return R.OK();
        else return R.error("更新失败");
    }

}
