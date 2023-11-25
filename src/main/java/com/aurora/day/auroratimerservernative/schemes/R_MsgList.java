package com.aurora.day.auroratimerservernative.schemes;

import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来承载多个错误参数的情况
 */
@Data
public class R_MsgList {
    int code;
    Object msgList;

    public R_MsgList(ResponseState state, Object msgList) {
        this.code = state.getCode();
        this.msgList = msgList;
    }
    public static R_MsgList error(ResponseState state) {
        List<String> list = new ArrayList<>();
        list.add(state.getMsg());
        return new R_MsgList(state,list);
    }

}
