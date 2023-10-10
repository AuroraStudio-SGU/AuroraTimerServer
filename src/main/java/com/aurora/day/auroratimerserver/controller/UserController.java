package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.pojo.User;
import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.request.LoginRequest;
import com.aurora.day.auroratimerserver.schemes.request.RegisterRequest;
import com.aurora.day.auroratimerserver.schemes.request.updateUserRequest;
import com.aurora.day.auroratimerserver.service.IUserService;
import com.aurora.day.auroratimerserver.servicelmpl.UserServiceImpl;
import com.aurora.day.auroratimerserver.utils.TokenUtil;
import com.aurora.day.auroratimerserver.vo.UserVo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
@Tag(name = "UserController", description = "用户接口")
public class UserController {

    private static final Log logger = LogFactory.get();

    private final IUserService userService;

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
    @PostMapping("/user/uploadAvatar/{id}")
    public R avatar(@Parameter(name = "file")MultipartFile file,@PathVariable(name = "id")String id){
        String fileName = "Avatar-"+id + ".png";
        String avatarPath = File.separator + "avatars" + File.separator + fileName;
        //若之前存在过头像则直接覆写
        File avatar = FileUtil.file(TimerConfig.filePath+avatarPath);
        try {
            FileUtil.writeFromStream(file.getInputStream(),avatar);
        } catch (IOException e) {
            logger.error("控制层IO异常{}",e.getLocalizedMessage());
            return R.error("上传图片失败,IO写入失败",e,true);
        }
        String linkUrl = "http://" + TimerConfig.publicHost + avatarPath.replaceAll("\\\\","/");
        if(userService.updateUser(new User(id,null,null,linkUrl,false,false,0,0))) return R.OK(linkUrl);
        else return R.error("更新失败");
    }
    @GetMapping("/user/newToken/{uid}")
    public R generateToken(@PathVariable("uid")String uid){
        User user = userService.queryUserById(uid);
        if(user==null) return R.error("用户不存在");
        return R.OK(TokenUtil.createToken(user.getId(),user.isAdmin()));
    }

    @GetMapping("/user/loginByToken/{token}")
    public R loginWithToken(@PathVariable("token")String token){
        String uid = TokenUtil.getId(token);
        if(uid!=null){
            User user = userService.queryUserById(uid);
            UserVo vo = BeanUtil.toBean(user, UserVo.class);
            vo.setToken(TokenUtil.createToken(user.getId(),user.isAdmin()));
            return R.OK(vo);
        }else {
            return R.error("token不正确或token已过期");
        }
    }

}
