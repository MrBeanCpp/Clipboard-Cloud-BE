package com.mrbeanc.config;

import com.mrbeanc.model.Clipboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ClipboardConfig {
    /** （全局）剪贴板数据存储Map: id -> Clipboard */
    @Bean
    public Map<String, Clipboard> clipboards() {
        return new ConcurrentHashMap<>(); // key or value 不允许为null
    }
}
