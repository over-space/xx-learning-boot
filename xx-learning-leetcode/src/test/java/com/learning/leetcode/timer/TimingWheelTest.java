package com.learning.leetcode.timer;

import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

/**
 * @author over.li
 * @since 2022/7/21
 */
public class TimingWheelTest extends BaseTest {

    @Test
    void test() {

        SystemTimer systemTimer = new SystemTimer();

        systemTimer.add(new TimerTask(350L));

        sleep(5);
    }
}
