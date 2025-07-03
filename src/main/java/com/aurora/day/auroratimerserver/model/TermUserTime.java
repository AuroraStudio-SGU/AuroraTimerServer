package com.aurora.day.auroratimerserver.model;

import lombok.Data;

/**
 * 仅用来存储学期统计的中间类
 */
@Data
public class TermUserTime {
    private String uid;
    private long totalTime;
}
