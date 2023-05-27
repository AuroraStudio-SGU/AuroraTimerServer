package com.aurora.day.auroratimerserver.vo;

import lombok.Data;

@Data
public class UserOnlineTime {
    private String id;
    private String name;
    private long totalTime;
    private long weekTime;
}
