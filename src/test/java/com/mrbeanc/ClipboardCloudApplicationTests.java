package com.mrbeanc;

import com.mrbeanc.model.Clipboard;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClipboardCloudApplicationTests {

    @Test
    void testClipboardDTO() {
        Clipboard clipboard = new Clipboard();
        assert(clipboard.getData().isEmpty());
        assert(clipboard.getIsText());
    }

}
