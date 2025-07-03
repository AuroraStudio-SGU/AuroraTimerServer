package com.aurora.day.auroratimerserver.api;

import cn.hutool.crypto.SecureUtil;
import com.aurora.day.auroratimerserver.annotation.AdminRequired;
import com.aurora.day.auroratimerserver.annotation.LoginRequired;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.model.Result;
import com.aurora.day.auroratimerserver.model.User;
import com.aurora.day.auroratimerserver.model.vo.UserVo;
import com.aurora.day.auroratimerserver.schame.req.LoginReq;
import com.aurora.day.auroratimerserver.schame.req.RegistryUserReq;
import com.aurora.day.auroratimerserver.services.impl.UserService;
import com.aurora.day.auroratimerserver.utils.TokenUtil;
import org.noear.solon.annotation.Body;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Param;
import org.noear.solon.annotation.Path;
import org.noear.solon.annotation.Post;
import org.noear.solon.annotation.Put;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.UploadedFile;
import org.noear.solon.validation.annotation.NotEmpty;
import org.noear.solon.validation.annotation.Validated;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Mapping("user")
public class UserController extends BaseController {

    @Inject
    UserService userService;

    @Get
    @Mapping("{id}")
    public Result<UserVo> queryUser(@Path String id) {
        return Result.success(userService.queryUserVo(id));
    }

    @AdminRequired
    @Get
    @Mapping("all")
    public Result<List<UserVo>> queryAllUser() {
        return Result.success(userService.queryAllUsers(true));
    }

    @LoginRequired
    @Post
    @Mapping("modify")
    public Result<String> modifyUser(@Body User user) {
        user.setAdmin(null);//不允许从此处设置权限
        user.setPriv(null);//不允许从此处设置权限
        String id = getCurrentUserId();
        if (id == null || !id.equals(user.getId())) {
            return Result.fail("非法修改");
        }
        return Result.success(userService.updateUser(user));
    }


    @Post
    @Mapping("registry")
    public Result<String> registryUser(
            @Body @Validated RegistryUserReq req
    ) {
        User user = req.toUser();
        user.setPassword(null);//不允许从此处设置密码
        user.setAdmin(null);//不允许从此处设置权限
        user.setPriv(null);//不允许从此处设置权限
        user.setAvatar(TimerConfig.getDefaultAvatarPath());
        return Result.success(userService.registerUser(user));
    }

    @Post
    @Mapping("login")
    public Result<UserVo> loginUser(
            @Body @Validated LoginReq req
    ) {
        User u = userService.queryUser(req.getId());
        if (u == null) return Result.fail("用户不存在");
        if (u.getPassword().equals(req.getPassword())) {
            UserVo vo = u.toVo();
            vo.setToken(TokenUtil.createToken(vo.getId(), vo.getAdmin()));
            return Result.success(vo);
        } else {
            return Result.fail("密码错误");
        }
    }

    @Post
    @Mapping("login/token")
    public Result<UserVo> loginToken(
            @Body @NotEmpty("token不能为空") String token
    ) {
        if (TokenUtil.Verify(token)) {
            return Result.success(userService.queryUserVo(TokenUtil.getId(token)));
        } else {
            return Result.fail("登录失败");
        }
    }

    @LoginRequired
    @Post
    @Mapping("avatar/upload")
    public Result<String> uploadAvatar(UploadedFile file) {
        try {
            if (!file.getExtension().equals("png"))
                return Result.fail("只支持png格式");
            File avatarFolder = new File(TimerConfig.filePath, "avatar");
            String fileName = getCurrentUserId() + "-avatar.png";
            file.transferTo(new File(avatarFolder, fileName));
            String url = TimerConfig.hostPath + "/avatar/" + fileName;
            return Result.success(url);
        } catch (IOException e) {
            log.warn("文件转移失败:{}", e.getLocalizedMessage(), e);
        } finally {
            try {
                file.delete();
            } catch (IOException ignored) {
            }
        }
        return Result.success(false);
    }

    @Put
    @Mapping("psw/rest")
    public Result<String> resetPassword(
            @Param String id
    ) {
        User user = userService.queryUser(id);
        if (user == null) return Result.fail("用户不存在");
        if (user.getAdmin()) return Result.fail("管理员用户请从其它接口修改密码");
        user.setPassword(SecureUtil.md5("123456"));
        userService.updateUser(user);
        return Result.success("重置成功，密码为123456");
    }

}
