package com.learning.common.util;

import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;
import java.io.IOException;

/**
 * @author over.li
 * @since 2023/5/5
 */
public class FileUtilTest extends BaseTest {

    @Test
    void test() {
        String base64 = "";
        FileUtil.base64ToImage(base64, "/Users/flipos/Desktop");
    }

    @Test
    void test2() throws IOException {
        String base64 = DigestUtil.base64("/Users/flipos/Downloads/1.png");
        System.out.println(base64);
    }
}
