package com.mrbeanc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //开启定时任务
public class ClipboardCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClipboardCloudApplication.class, args);
    }

}
