package com.learning.spark

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

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

    test("distinct") {
        val context = getSparkContext("scala-test-01")

        val listRDD = context.parallelize(List(new Tuple2("hello", "word"), new Tuple2("hello", "scala"), Tuple2("hello", "word")))
        listRDD.foreach(println)

        line()
        val distinctRDD = listRDD.distinct()
        distinctRDD.foreach(println)
    }

    test("testWordCount") {

        val context = getSparkContext("scala-word-count-01")
        val fileRDD = context.textFile("xx-learning-spark/src/test/resources/data/dict.txt")

        val wordRDD = fileRDD.flatMap(_.split(" "))
        wordRDD.foreach(println)
        line()

        val pairRDD = wordRDD.map(Tuple2(_, 1))
        pairRDD.foreach(println)
        line()


        val result = pairRDD.reduceByKey(_ + _)
        result.foreach(println)
        line()
    }

    /**
     * 根据PV统计前5的网站
     */
    test("pv") {
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

        line()
        result.foreach(println)
    }

    /**
     * 统计UV数据量前5的网站
     */
    test("uv") {
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

        line()
        result.foreach(println)
    }

    test("sum,count,avg,max,min") {

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


    test("行转列") {
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

    test("分区操作01") {
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
        iteratorRDD1.foreach(println)
    }

    test("分区操作02") {
        var context: SparkContext = new SparkContext("local", "scala-partition-02");
        val dataRDD: RDD[(Int)] = context.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2)

        dataRDD.mapPartitionsWithIndex((pi, pt) => {

            print("连接数据库...")

            new Iterator[String] {
                override def hasNext: Boolean = {
                    if(pt.hasNext){
                        true
                    }else{
                        print("关闭数据库连接")
                        false;
                    }
                }

                override def next(): String = {
                    print("查询数据库.....")
                    s"select result $pi、" + pt.next()
                }
            }
        }).foreach(println)
    }
}
