package com.mrbeanc.model;

import lombok.Data;

@Data
public class Clipboard {
    private String os = "";
    private String data = "";
    private Boolean isText = true;

    @Override
    public String toString() {
        String _data;
        final int maxLen = 100;
        if (data.length() <= maxLen) {
            _data = data;
        } else {
            _data = data.substring(0, maxLen) + "...";
        }

        return "Clipboard{" +
                "os='" + os + '\'' +
                ", data='" + _data + '\'' +
                ", isText=" + isText +
                '}';
    }
}
