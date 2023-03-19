package com.learning.spark

import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.spark.rdd.RDD
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.{AfterAll, BeforeAll, TestInstance}

import scala.reflect.ClassTag

/**
 * @author over.li
 * @since 2023/3/17
 */
// @RunWith(classOf[JUnitRunner])
@TestInstance(Lifecycle.PER_CLASS)
class ScalaBaseTest extends Serializable {

    val logger: Logger = LogManager.getLogger(this.getClass)

    def line(): Unit = {
        logger.info("*************************************************************************************")
    }

    def print(msg: String): Unit = {
        logger.info(s"$msg")
    }

    @BeforeAll
    def before(): Unit = {
        logger.info("================================================================================================")
        logger.info("-------------------------------------开始执行测试方法---------------------------------------------")
        logger.info("")
    }

    @AfterAll
    def after(): Unit = {
        logger.info("")
        logger.info("-------------------------------------测试方法执行完成---------------------------------------------")
        logger.info("================================================================================================")
    }

    def console[T](iterator:RDD[T]): Unit = {
        logger.info("")
        iterator.foreach(v => {
            logger.info(s"$v")
        })
        logger.info("")
    }
    def console[T](list:Array[T]): Unit = {
        logger.info("")
        list.foreach(v => {
            logger.info(s"$v")
        })
        logger.info("")
    }
}
