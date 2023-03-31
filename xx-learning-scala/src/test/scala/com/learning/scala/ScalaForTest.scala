package com.learning.scala

import com.learning.scala.logger.ScalaBaseTest
import org.junit.jupiter.api.Test

import scala.collection.mutable

/**
 * @author over.li
 * @since 2023/3/30
 */
class ScalaForTest extends ScalaBaseTest {

    @Test
    def testFor01(): Unit = {

        def find(nums: Seq[Int], target: Int): Int = {
            for (i <- nums.indices) {
                if (nums(i) == target) {
                    return i;
                }
            }
            0
        }

        var nums: Seq[Int] = 1 to 10;
        val result = find(nums, 5)
        logger.info("result: {}", result)
    }

    @Test
    def testFor02(): Unit = {
        val map = new mutable.HashMap[Int, Int]();
        var map2 = map + (1 -> 1)
        map2.foreach(k => print(s"${k._1}\t${k._2}"))
    }

    @Test
    def testFor3(): Unit = {
        // val map = arr.zipWithIndex.toMap // 将数组转换为 (元素, 下标) 的 Map
        // val result = for {
        //     i <- arr.indices // 遍历下标
        //     j <- map.get(target - arr(i)) // 尝试获取另一个元素的下标
        //     if i != j // 排除自身
        // } yield Array(i, j) // 返回结果数组
        // result.headOption.getOrElse(Array(0)) // 如果结果为空，就返回 Array(0)

        var target = 11;
        var nums = Array(1, 2, 3, 4, 5, 6, 7, 8, 5);
        val map = nums.zipWithIndex.toMap
        map.foreach(k => print(s"${k._1}\t${k._2}"))

       var result =  for{
            i <- nums.indices
            j <- map.get(target - nums(i))
            if i != j
        } yield Array(i, j)

        val array = result.headOption.getOrElse(Array(0))
        array.foreach(println)
    }

    @Test
    def testFor04():Unit = {

       var list =  for{
            i <- 0 to 10
            j <- 5 to 15
            if( i != j)
            if( i > 5)
            if( j > 10)
        } yield Array(i, j)

        list.foreach(x => logger.info("i={}, j={}", x(0), x(1)))
    }
}
