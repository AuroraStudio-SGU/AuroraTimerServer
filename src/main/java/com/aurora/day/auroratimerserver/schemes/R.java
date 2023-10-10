package com.aurora.day.auroratimerserver.schemes;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
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
        else return R.error("系统错误");
    }

    public static R OK(JSONObject json) {
        return new R(200, "操作成功", json);
    }
    public static R OK(JSONArray array) {
        return new R(200, "操作成功", array);
    }

    public static R OK(Object object) {
        return new R(200, "操作成功", object);
    }

    public static R OK() {
        return new R(200, "操作成功", null);
    }

    public static R error(String msg) {
        return new R(100, msg, null);
    }

    public static R error(int code,String msg){return new R(code,msg,null); }
    public static R error(String msg,Object obj){return new R(100,msg,obj); }

    public static R error(String msg,Exception e ,boolean isFullTrack){
        JSONObject error = new JSONObject();
        error.set("reason", e.getLocalizedMessage());
        if(isFullTrack){
            error.set("stacks", e.getStackTrace());
        }else {
            error.set("stacks", Arrays.copyOfRange(e.getStackTrace(),0,10));
        }
        return R.error(msg,error);
    }

    public static R_MsgList errorList(List<String> errors){
        return new R_MsgList(100,new JSONArray(errors));
    }
    public static R_MsgList errorList(String ...errors){
        return new R_MsgList(100,new JSONArray(errors));
    }
}