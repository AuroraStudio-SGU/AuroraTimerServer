package com.aurora.day.auroratimerservernative.utils;

import com.aurora.day.auroratimerservernative.pojo.User;
import com.aurora.day.auroratimerservernative.schemes.request.RegisterRequest;
import com.aurora.day.auroratimerservernative.schemes.request.updateUserRequest;
import com.aurora.day.auroratimerservernative.pojo.UserVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConvertMapper {
    UserVo toVo(User user);
    User toUser(updateUserRequest request);
    User toUser(RegisterRequest request);

}
