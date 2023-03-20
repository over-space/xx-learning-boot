package com.learning.scala

import org.apache.logging.log4j.{LogManager, Logger}
import org.junit.jupiter.api.{AfterAll, BeforeAll, TestInstance}
import org.junit.jupiter.api.TestInstance.Lifecycle

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

    def console[T](list:Array[T]): Unit = {
        logger.info("")
        list.foreach(v => {
            logger.info(s"$v")
        })
        logger.info("")
    }

    def pause(): Unit = {
        while (true){

        }
    }
}
