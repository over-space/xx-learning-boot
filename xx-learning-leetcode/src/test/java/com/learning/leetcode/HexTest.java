package com.learning.leetcode;

import com.learning.logger.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author over.li
 * @since 2022/10/14
 */
public class HexTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(HexTest.class);

    public int[] twoSum(int[] arr, int target){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if(map.containsKey(target - arr[i])){
                return new int[]{map.get(target - arr[i]), i};
            }
            map.put(arr[i], i);
        }
        return new int[]{0};
    }


    @Test
    void testTime(){
        long time = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long t = 1679559978865L;
        System.out.println(time);
        System.out.println(t);
        System.out.println((time - t) / 1000 / 60);
    }
    @Test
    public void test() {
        String result1 = fun(7, 5);
        logger.info("result: {}", result1);

        String result2 = fun(15, 5);
        logger.info("result: {}", result2);

        String result3 = fun(15, 2);
        logger.info("result: {}", result3);
    }

    private String fun(int num, int hex) {
        String result = "";

        int mod;
        int value = num;

        while (value > 0) {
            mod = value % hex;
            value = value / hex;

            result = mod + result;
        }
        return result;
    }
}
