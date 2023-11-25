package com.aurora.day.auroratimerservernative.schemes.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class setDutyRequest {
    @NotEmpty(message = "不能为空")
    private String wed;
    @NotEmpty(message = "不能为空")
    private String sun;
}
