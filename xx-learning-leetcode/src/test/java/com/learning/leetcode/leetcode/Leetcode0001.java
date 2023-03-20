package com.learning.leetcode.leetcode;


import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author lifang
 * @since 2021/1/14
 */
public class Leetcode0001 extends BaseTest {


    @Test
    public void run() {
        prefixesDivBy5(new int[]{0, 1, 1});
    }

    public List<Boolean> prefixesDivBy5(int[] A) {

        int num = 0;
        for (int i : A) {
            num <<= 1;

            System.out.println(num);
            num += A[i];

            System.out.println(num);

            num %= 10;

            System.out.println(num % 5 == 0);

        }

        return null;
    }


}
