package com.aurora.day.auroratimerservernative.schemes.response;

import lombok.Data;

import java.util.List;

@Data
public class UserTimeDetailResponse {


    private List<String> dates;

    private List<String> times;
}
