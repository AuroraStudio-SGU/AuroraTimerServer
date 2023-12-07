package com.aurora.day.auroratimerservernative.schemes.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageData implements Serializable {
    String url;
    String alt;
    String href;

    public ImageData(String url, String alt) {
        this.url = url;
        this.alt = alt;
    }
}