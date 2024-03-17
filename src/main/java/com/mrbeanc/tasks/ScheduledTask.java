package com.mrbeanc.tasks;

import com.mrbeanc.model.Clipboard;
import com.mrbeanc.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTask {
    private final int EXPIRE_MINUTES;
    private final Map<String, Clipboard> clipboards;

    @Autowired
    public ScheduledTask(@Value("${clipboard.expire.minutes}") int expireMinutes,
                         @Qualifier("clipboards") Map<String, Clipboard> clipboards) {
        EXPIRE_MINUTES = expireMinutes;
        this.clipboards = clipboards;
    }

    /** 每隔一段时间，清理过期的剪切板（保护隐私 & 减轻内存压力） */
    @Scheduled(fixedRateString = "${clipboard.clean.minutes}", initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void clipboardClean() {
        System.out.println("#ScheduledTask.Clean");
        int lastSize = clipboards.size();

        // Remove clipboards that are older than 10 minutes
        clipboards.entrySet().removeIf(entry -> {
            Duration duration = Duration.between(entry.getValue().getTime(), Instant.now());
            boolean isExpired = duration.toMinutes() >= EXPIRE_MINUTES;
            if (isExpired) {
                System.out.println("\tclean: " + Utils.omitSHA256(entry.getKey()) + " - " + entry.getValue());
            }
            return isExpired;
        });

        int curSize = clipboards.size();
        if (lastSize != curSize) {
            System.out.println("\t" + lastSize + " -> " + curSize);
        }
    }
}
