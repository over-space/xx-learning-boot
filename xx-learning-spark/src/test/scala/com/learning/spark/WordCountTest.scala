package com.learning.spark

import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.junit.jupiter.api.Test

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


/**
 * @author over.li
 * @since 2023/3/17
 */
class WordCountTest extends ScalaBaseTest {

    def getSparkContext(appName: String): SparkContext = {
        var content: SparkContext = new SparkContext("local", appName);
        content.setLogLevel("ERROR")
        content
    }

    @Test
    def testLogger(): Unit = {
        line()
    }

    @Test
    def testDistinct {
        val context = getSparkContext("scala-test-01")

        val listRDD: RDD[Tuple2[String, String]] = context.parallelize(List(new Tuple2("hello", "word"), new Tuple2("hello", "scala"), Tuple2("hello", "word")))
        listRDD.foreach(println)

        line()
        val distinctRDD = listRDD.distinct()
        distinctRDD.foreach(println)
    }

    @Test
    def testWordCount {

        val context = getSparkContext("scala-word-count-01")
        val fileRDD = context.textFile("src/test/resources/data/dict.txt")

        val wordRDD = fileRDD.flatMap(_.split(" "))
        console(wordRDD)

        val pairRDD = wordRDD.map(Tuple2(_, 1))
        console(pairRDD)

        val result = pairRDD.reduceByKey(_ + _)
        console(result)
    }

    /**
     * 根据PV统计前5的网站
     */
    @Test
    def testPV {
        // 119.84.168.158	贵州	  2018-11-12	1542011088715	5857482780010208273	www.dangdang.com	Buy
        val context = getSparkContext("scala-pv-01")
        val fileRDD = context.textFile("xx-learning-spark/src/test/resources/data/pvuv.txt", 5)

        val pairRDD: RDD[(String, Int)] = fileRDD.map(line => (line.split("\t")(5), 1))
        val pvTotalRDD: RDD[(String, Int)] = pairRDD.reduceByKey(_ + _)

        //    val swapPVTotalRDD: RDD[(Int, String)] = pvTotalRDD.map(_.swap)
        //    val sortedRDD: RDD[(Int, String)] = swapPVTotalRDD.sortByKey(false)
        //    val sortedSwapRDD: RDD[(String, Int)] = sortedRDD.map(_.swap)
        val sortedSwapRDD = pvTotalRDD.sortBy(_._2, false)

        val result: Array[(String, Int)] = sortedSwapRDD.take(5)

        console(result)
    }

    /**
     * 统计UV数据量前5的网站
     */
    @Test
    def testUV {
        // 119.84.168.158	贵州	  2018-11-12	1542011088715	5857482780010208273	www.dangdang.com	Buy
        val context = getSparkContext("scala-uv-01")
        val fileRDD = context.textFile("xx-learning-spark/src/test/resources/data/pvuv.txt", 5)

        val pairRDD = fileRDD.map(line => {
            val split: Array[String] = line.split("\t")
            (split(5), split(0))
        })

        // 去重
        val distinctRDD: RDD[(String, String)] = pairRDD.distinct()

        val countRDD = distinctRDD.map(k => (k._1, 1))

        val totalRDD = countRDD.reduceByKey(_ + _)

        val sortedRDD = totalRDD.sortBy(_._2, false)
        val result = sortedRDD.take(5)

        console(result)
    }

    @Test
    def testSumCountMaxMinAvg {

        val context = getSparkContext("scala-sum-01")

        val dataRDD: RDD[(String, Int)] = context.parallelize(List(("zhangsan", 85), ("zhangsan", 74),
            ("lisi", 66), ("lisi", 59), ("jay", 100), ("wangwu", 53), ("wangwu", 35), ("wangwu", 61)
        ))

        print("sum")
        val sum = dataRDD.reduceByKey(_ + _)
        sum.foreach(println)

        print("count")
        val count = dataRDD.mapValues(k => 1).reduceByKey(_ + _)
        count.foreach(println)

        print("max")
        val max = dataRDD.reduceByKey((k1, k2) => if (k1 > k2) k1 else k2)
        max.foreach(println)

        print("min")
        val min = dataRDD.reduceByKey((k1, k2) => if (k1 < k2) k1 else k2)
        min.foreach(println)

        print("avg")
        val countRDD = sum.join(count)
        val avg = countRDD.mapValues(v => v._1 / v._2)
        avg.foreach(println)

        print("combineByKey")
        val combineRDD: RDD[(String, (Int, Int))] = dataRDD.combineByKey(
            (v: Int) => {
                (v, 1)
            },
            (oldValue: (Int, Int), newValue: Int) => {
                (oldValue._1 + newValue, oldValue._2 + 1)
            },
            (v1: (Int, Int), v2: (Int, Int)) => {
                (v1._1 + v2._1, v1._2 + v2._2)
            })

        val avg2 = combineRDD.mapValues(v => v._1 / v._2)
        avg2.foreach(println)

    }


    @Test
    def testRowToColumn(): Unit = {
        val context = getSparkContext("scala-row-01")

        val dataRDD: RDD[(String, Int)] = context.parallelize(List(("zhangsan", 85), ("zhangsan", 74),
            ("lisi", 66), ("lisi", 59), ("jay", 100), ("wangwu", 53), ("wangwu", 35), ("wangwu", 61)
        ))

        val groupRDD = dataRDD.groupByKey()
        groupRDD.foreach(println)
        line()

        val result: RDD[(String, Int)] = groupRDD.flatMap(k => k._2.map(v => (k._1, v)).iterator)
        result.foreach(println)

    }

    @Test
    def testPartition01 {
        var context: SparkContext = new SparkContext("local", "scala-partition-01");
        val dataRDD: RDD[(Int)] = context.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2)

        val iteratorRDD1: RDD[(String)] = dataRDD.mapPartitionsWithIndex((pi, pt) => {
            print("连接数据库.....")
            print("查询数据")
            val list: ListBuffer[String] = new ListBuffer[String]
            while (pt.hasNext) {
                val num = pt.next()
                list.+=(s"selected result $pi - $num")
            }
            print("关闭数据库连接....")
            list.iterator
        })
        console(iteratorRDD1)
    }

    @Test
    def testPartition02 {
        var context: SparkContext = new SparkContext("local", "scala-partition-02");
        val dataRDD: RDD[(Int)] = context.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2)

        val result = dataRDD.mapPartitionsWithIndex((pi, pt) => {

            print("连接数据库...")

            new Iterator[String] {
                override def hasNext: Boolean = {
                    if (pt.hasNext) {
                        true
                    } else {
                        print("关闭数据库连接")
                        false;
                    }
                }

                override def next(): String = {
                    print("查询数据库.....")
                    s"select result $pi、" + pt.next()
                }
            }
        })

        console(result)
    }

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
