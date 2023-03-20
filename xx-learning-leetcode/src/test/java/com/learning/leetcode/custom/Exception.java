package com.learning.leetcode.custom;

import com.learning.leetcode.leetcode.Testing;
import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author lifang
 * @since 2022/1/5
 */
public class Exception extends BaseTest implements Testing {

    @Test
    public void test() {
        try {

            int i = 1 / 0;

        } catch (java.lang.Exception e) {
            e.printStackTrace();
            System.out.println("-------------------------------------------------------------------------------");
            logger.info(e.getMessage(), e);
            System.out.println("-------------------------------------------------------------------------------");
        }
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime current = LocalDateTime.now();
        System.out.println(current.getMonth().getValue());
        System.out.println(current.getMonthValue());
    }
}
