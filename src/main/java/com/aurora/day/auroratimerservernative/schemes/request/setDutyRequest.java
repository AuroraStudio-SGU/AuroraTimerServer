package com.aurora.day.auroratimerservernative.schemes.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class setDutyRequest {
    @NotEmpty(message = "不能为空")
    @Schema(name = "wed",description = "周三人员")
    private String wed;
    @NotEmpty(message = "不能为空")
    @Schema(name = "sun",description = "周日人员")
    private String sun;
}
