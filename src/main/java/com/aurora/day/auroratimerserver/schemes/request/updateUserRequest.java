package com.aurora.day.auroratimerserver.schemes.request;

import lombok.Data;

@Data
public class updateUserRequest {
    private String id;
    private String name;
    private String password;
    private String avatar;
    private boolean admin;
}
