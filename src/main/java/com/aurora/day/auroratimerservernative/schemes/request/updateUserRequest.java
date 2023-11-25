package com.aurora.day.auroratimerservernative.schemes.request;

import lombok.Data;

@Data
public class updateUserRequest {
    private String id;
    private String name;
    private String avatar;
    private String major;
    private String grade;
    private String work_group;
    private boolean afk;
}
