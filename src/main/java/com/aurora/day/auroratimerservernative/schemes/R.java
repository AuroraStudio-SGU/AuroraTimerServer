package com.aurora.day.auroratimerservernative.schemes;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.aurora.day.auroratimerservernative.utils.JsonHelper;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.noear.snack.ONode;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RegisterReflectionForBinding(R.class)
@Data
public class R implements Serializable {
    int code;
    String msg;
    Object data;

    public R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R auto(boolean flag) {
        if (flag) return R.OK(true);
        else return new R(ResponseState.ERROR.getCode(), "系统错误", null);
    }

    public static R OK(Object object) {
        return new R(200, "操作成功", object);
    }

    public static String OKSTR(Object object) {
        return ONode.loadObj(new R(200, "操作成功", object)).toJson();
    }

    public static R OK() {
        return new R(200, "操作成功", null);
    }

    public static R error(ResponseState state) {
        return new R(state.getCode(), state.getMsg(), null);
    }

    public static String errorSTR(ResponseState state) {
        return ONode.loadObj(new R(state.getCode(), state.getMsg(), null)).toJson();
    }

    public static R error(ResponseState state, String msg) {
        return new R(state.getCode(), msg, null);
    }

    public static R error(ResponseState state, JSONObject object) {
        return new R(state.getCode(), state.getMsg(), object);
    }

    public static R error(ResponseState state, ObjectNode object) {
        return new R(state.getCode(), state.getMsg(), object);
    }

    public static R error(ResponseState state, Throwable e, boolean isFullTrack) {
        ObjectNode node = JsonHelper.newEmptyNode();
        String error_msg = e.getLocalizedMessage();
        node.put("reason", error_msg == null ? "大概率是空指针,总之没有消息" : error_msg);
        if (isFullTrack) {
            node.set("stacks", JsonHelper.newArrayNode(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())));
        } else {
            node.set("stacks", JsonHelper.newArrayNode(Arrays.copyOfRange(e.getStackTrace(), 0, 10)));
        }
        return R.error(state, node);
    }

    public static R_MsgList errorList(ResponseState state, List<String> errors) {
        errors.add(state.getMsg());
        return new R_MsgList(state, new JSONArray(errors));
    }

    public static R_MsgList errorList(ResponseState state, String... errors) {
        JSONArray array = new JSONArray(errors);
        array.add(state.getMsg());
        return new R_MsgList(state, array);
    }

    public static R Ok(String data) {
        return new R(200, "操作成功", data);
    }
}