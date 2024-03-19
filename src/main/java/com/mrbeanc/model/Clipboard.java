package com.mrbeanc.model;

import com.mrbeanc.util.Utils;
import lombok.Data;

import java.time.Instant;

@Data
public class Clipboard {
    private String os = "";
    private String data = "";
    private Boolean isText = true;
    private Instant time = Instant.now();

    @Override
    public String toString() {
        String _data = isText ?
                Utils.omit(data, 10) + "[Text Base64]" :
                Utils.omit(data, 10) + "[Image Base64]";

        return "Clipboard{" +
                "os='" + os + '\'' +
                ", data='" + _data + '\'' +
                ", isText=" + isText +
                ", time=" + Utils.toLocalTime(time) +
                '}';
    }
}
