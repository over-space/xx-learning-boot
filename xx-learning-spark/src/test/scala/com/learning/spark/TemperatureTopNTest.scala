package com.learning.spark

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.junit.jupiter.api.Test

import scala.collection.mutable

/**
 * @author over.li
 * @since 2023/3/20
 */
class TemperatureTopNTest extends SparkBaseTest {

    @Test
    def testTemperatureTopN01(): Unit = {
        var context: SparkContext = new SparkContext("local", "scala-temperature-02");
        val fileRDD = context.textFile("src/test/resources/data/temperature.txt")

        val dataRDD = fileRDD.map(line => line.split(" "))
            .map(arr => {
                val dateArray: Array[String] = arr(0).split("-")
                // (year, month, day, temperature)
                (dateArray(0).toInt, dateArray(1).toInt, dateArray(2).toInt, arr(1).toInt)
            })

        // 按年月分组
        val groupRDD = dataRDD.map(t4 => ((t4._1, t4._2), (t4._3, t4._4))).groupByKey()
        console(groupRDD)

        val value = groupRDD.mapValues(arr => {
            // 去重
            val map = new mutable.HashMap[Int, Int]()
            arr.foreach(temp => {
                if (map.getOrElse(temp._1, 0) < temp._2) {
                    map.put(temp._1, temp._2)
                }
            })
            map.toList.sorted(new Ordering[(Int, Int)] {
                // 温度排序
                override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compare(x._2)
            }).take(2)
        })
        console(value)
    }

    @Test
    def testTemperatureTopN02(): Unit = {
        var context: SparkContext = new SparkContext("local", "scala-temperature-02");
        val fileRDD = context.textFile("src/test/resources/data/temperature.txt")

        implicit def xxx = new Ordering[(Int, Int)] {
            override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compare(x._2);
        }

        val dataRDD = fileRDD.map(line => line.split(" "))
            .map(arr => {
                val dateArray: Array[String] = arr(0).split("-")
                // (year, month, day, temperature)
                (dateArray(0).toInt, dateArray(1).toInt, dateArray(2).toInt, arr(1).toInt)
            })

        // 按年月日去重,并且保留最大值
        val distRDD = dataRDD.map(arr => ((arr._1, arr._2, arr._3), arr._4))
            .reduceByKey((x: Int, y: Int) => if (x > y) x else y)
        console(distRDD)

        // ((年，月)，(日，温度))
        val yearMonthRDD = distRDD.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))

        // 相同年月为一组
        val groupRDD = yearMonthRDD.groupByKey()

        // 排序取最大的两条
        val result = groupRDD.mapValues(arr => arr.toList.sorted.take(2))

        console(result)
    }

    @Test
    def testTemperatureTopN03(): Unit = {
        var context: SparkContext = new SparkContext("local", "scala-temperature-02");
        val fileRDD = context.textFile("src/test/resources/data/temperature.txt")

        val dataRDD = fileRDD.map(line => line.split(" "))
            .map(arr => {
                val dateArray: Array[String] = arr(0).split("-")
                // (year, month, day, temperature)
                (dateArray(0).toInt, dateArray(1).toInt, dateArray(2).toInt, arr(1).toInt)
            })

        // 按年月温度排序
        val sorted: RDD[(Int, Int, Int, Int)] = dataRDD.sortBy(t4 => (t4._1, t4._2, t4._4), false)

        // 用reduceByKey去重
        val reduced: RDD[((Int, Int, Int), Int)] = sorted.map(t4 => ((t4._1, t4._2, t4._3), t4._4))
            .reduceByKey((x: Int, y: Int) => if (y > x) y else x)

        val maped: RDD[((Int, Int), (Int, Int))] = reduced.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))
        val grouped: RDD[((Int, Int), Iterable[(Int, Int)])] = maped.groupByKey()
        console(grouped)
    }
}
