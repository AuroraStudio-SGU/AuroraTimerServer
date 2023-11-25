package com.aurora.day.auroratimerservernative.schemes.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserTimeDetailResponse {

    @Schema(name = "dates",description = "对应的日期数组")
    private List<String> dates;
    @Schema(name = "dates",description = "对应的日期当天的计时时长,单位小时")
    private List<String> times;
}
