package com.aurora.day.auroratimerserver.schame.req;

import lombok.Data;
import org.noear.solon.validation.annotation.NotEmpty;

@Data
public class SetDutyReq {
    @NotEmpty(message = "wed值不能为空")
    //周三人选
    private String wed;
    @NotEmpty(message = "sun值不能为空")
    //周日人选
    private String sun;
}
