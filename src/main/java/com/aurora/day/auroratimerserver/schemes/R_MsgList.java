package com.aurora.day.auroratimerserver.schemes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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

    public R_MsgList(int code, Object msgList) {
        this.code = code;
        this.msgList = msgList;
    }
    public static R_MsgList error(String msg) {
        return new R_MsgList(101, msg);
    }

}
