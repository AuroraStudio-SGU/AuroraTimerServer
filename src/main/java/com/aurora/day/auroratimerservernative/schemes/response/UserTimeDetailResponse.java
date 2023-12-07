package com.aurora.day.auroratimerservernative.schemes.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserTimeDetailResponse implements Serializable {

    private List<String> dates;

    private List<String> times;
}
