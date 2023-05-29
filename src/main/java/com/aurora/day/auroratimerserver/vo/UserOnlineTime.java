package com.aurora.day.auroratimerserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOnlineTime {
    private String id;
    private String name;
    private long totalTime;
    private long weekTime;
}
