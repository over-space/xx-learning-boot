package com.learning.spark

import org.apache.spark.SparkContext

/**
 * @author over.li
 * @since 2023/3/17
 */
class WordCountTest extends ScalaBaseTest {

  def getSparkContext(appName:String): SparkContext = {
    var content: SparkContext = new SparkContext("local", appName);
    content.setLogLevel("ERROR")
    content
  }

  test("testWordCount"){

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

}
