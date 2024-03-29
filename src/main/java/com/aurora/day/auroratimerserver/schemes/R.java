package com.aurora.day.auroratimerserver.schemes;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.aurora.day.auroratimerserver.schemes.eums.ResponseState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@Schema(name = "R",description = "通用的返回对象")
public class R implements Serializable {
    @Schema(name = "code",description = "状态码",example = "200")
    int code;
    @Schema(name = "msg",description = "消息",example = "操作成功")
    String msg;
    @Schema(name = "data",description = "返回的JSON数据,若不需要,则为null")
    Object data;

    public R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R auto(boolean flag){
        if(flag) return R.OK(true);
        else return new R(ResponseState.ERROR.getCode(), "系统错误",null);
    }

    public static R OK(Object object) {
        return new R(200, "操作成功", object);
    }

    public static R OK() {
        return new R(200, "操作成功", null);
    }

    public static R error(ResponseState state){
        return new R(state.getCode(),state.getMsg(),null);
    }

    public static R error(ResponseState state,Object obj){return new R(state.getCode(),state.getMsg(),obj); }

    public static R error(ResponseState state,Throwable e ,boolean isFullTrack){
        JSONObject error = new JSONObject();
        String error_msg = e.getLocalizedMessage();
        error.set("reason", error_msg==null?"大概率是空指针,总之没有消息":error_msg);
        if(isFullTrack){
            error.set("stacks", e.getStackTrace());
        }else {
            error.set("stacks", Arrays.copyOfRange(e.getStackTrace(),0,10));
        }
        return R.error(state,error);
    }

    public static R_MsgList errorList(ResponseState state,List<String> errors){
        errors.add(state.getMsg());
        return new R_MsgList(state,new JSONArray(errors));
    }
    public static R_MsgList errorList(ResponseState state,String ...errors){
        JSONArray array = new JSONArray(errors);
        array.add(state.getMsg());
        return new R_MsgList(state,array);
    }
}