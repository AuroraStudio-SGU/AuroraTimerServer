package com.aurora.day.auroratimerservernative.schemes.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadImageResponse implements Serializable {
    int errno;
    String message;
    ImageData data;

    public UploadImageResponse(){
        errno = 0;
    }
}


