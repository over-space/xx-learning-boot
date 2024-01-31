package com.learning.leetcode.leetcode.dp;

import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

/**
 * @author over.li
 * @since 2023/11/22
 */
public class DynamicProgrammingTest extends BaseTest {

    /**
     * 斐波那契数
     */
    @Test
    void fibonacci(){
        int result = fibonacci(9);
        logger.info("result: {}", result);
    }

    /**
     * 爬楼梯,每次只能爬1节或2节楼梯
     */
    @Test
    void staircases(){
        staircases(20);
    }

    /**
     *  不同路径
     */
    @Test
    void paths(){
        paths(3, 3);
    }

    private int paths(int m, int n){
        if(m <= 0 || n <= 0) return 0;

        int[][] dp = new int[m + 1][n + 1];
        return 0;
    }

    private int fibonacci(int n) {
        if(n == 0) return 0;
        if(n == 1) return 1;

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    private int staircases(int n){
        if(n == 0) return 0;
        if(n == 1) return 1;
        if(n == 2) return 2;

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
