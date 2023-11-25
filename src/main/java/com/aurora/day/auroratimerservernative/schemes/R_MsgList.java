package com.aurora.day.auroratimerservernative.schemes;

import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来承载多个错误参数的情况
 */
@Data
@Schema(name = "R_MsgList",description = "有多个参数错误的情况")
public class R_MsgList {
    @Schema(name = "code",description = "错误码",example = "201")
    int code;
    @Schema(name = "msgList",description = "多个错误消息",type = "array",subTypes = String.class
    ,example = "[\n" +
            "        \"用户id不能为空\",\n" +
            "        \"日期不能为空\",\n" +
            "        \"日期类型为空\"\n" +
            "    ]"
    )
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
