package com.learning.spark

import org.apache.spark.SparkContext

/**
 * @author over.li
 * @since 2023/3/17
 */
object HelloScala {

  def main(args: Array[String]): Unit = {
    println("---------------Hello Scala-----------------")
  }

  def spark(): Unit = {
    var content: SparkContext = new SparkContext("local", "scala-word-count-01");

    val fileRDD = content.textFile("xx-learning-spark/src/test/resources/data/dict.txt")

    val wordRDD = fileRDD.map(_.split(" "))

    wordRDD.foreach(println)
  }
}
