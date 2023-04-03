package com.learning.spark

import com.learning.scala.logger.ScalaBaseTest
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @author over.li
 * @since 2023/3/20
 */
class SparkBaseTest extends ScalaBaseTest {

    def console[T](iterator: RDD[T]): Unit = {
        logger.info("")
        iterator.foreach(v => {
            logger.info(s"$v")
        })
        logger.info("")
    }

    def getSparkContext(appName: String): SparkContext = {
        var content: SparkContext = new SparkContext("local", appName);
        content.setLogLevel("ERROR")
        content
    }
}
