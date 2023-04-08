package com.learning.scala.leetcode

import com.learning.scala.logger.ScalaBaseTest
import org.junit.jupiter.api.Test

import scala.collection.mutable

/**
 * @author over.li
 * @since 2023/3/29
 */
class Leetcode0001 extends ScalaBaseTest{

    @Test
    def test(): Unit = {
        val result = twoSum02(Array(2, 7, 11, 15), 9)
        logger.info("result : {}", result)
    }

    def twoSum02(arr: Array[Int], target: Int): Array[Int] = {
        val map = new mutable.HashMap[Int, Int]()
        for (i <- arr.indices) {
            if (map.contains(target - arr(i))) {
                return Array(map(target - arr(i)), i)
            }
            map(arr(i)) = i
        }
        Array(0)
    }

    def twoSum03(arr: Array[Int], target: Int): Array[Int] = {

        def loop(map: Map[Int, Int], i: Int): Array[Int] = {
            if (i >= arr.length) Array(0) // 没有找到
            else if (map.contains(target - arr(i))) Array(map(target - arr(i)), i) // 找到了
            else loop(map + (arr(i) -> i), i + 1) // 继续递归
        }

        loop(Map.empty, 0) // 初始时 map 为空，i 为 0
    }

    def twoSum04(arr: Array[Int], target: Int): Array[Int] = {
        val map = arr.zipWithIndex.toMap // 将数组转换为 (元素, 下标) 的 Map
        val result = for {
            i <- arr.indices // 遍历下标
            j <- map.get(target - arr(i)) // 尝试获取另一个元素的下标
            if i != j // 排除自身
        } yield Array(i, j) // 返回结果数组
        result.headOption.getOrElse(Array(0)) // 如果结果为空，就返回 Array(0)
    }
}
