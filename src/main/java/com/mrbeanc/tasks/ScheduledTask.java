package com.mrbeanc.tasks;

import com.mrbeanc.model.Clipboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTask {
    private final int EXPIRE_MINUTES = 10;
    private final Map<String, Clipboard> clipboards;

    @Autowired
    public ScheduledTask(@Qualifier("clipboards") Map<String, Clipboard> clipboards) {
        this.clipboards = clipboards;
    }

    /** 每隔一段时间，清理过期的剪切板（保护隐私 & 减轻内存压力） */
    @Scheduled(fixedRate = 60, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void clipboardClean() {
        System.out.println("#ScheduledTask.Clean");
        int lastSize = clipboards.size();

        // Remove clipboards that are older than 10 minutes
        clipboards.entrySet().removeIf(entry -> {
            Duration duration = Duration.between(entry.getValue().getTime(), Instant.now());
            return duration.toMinutes() >= EXPIRE_MINUTES;
        });

        int curSize = clipboards.size();
        if (lastSize != curSize) {
            System.out.println("\t" + lastSize + " -> " + curSize);
        }
    }
}
