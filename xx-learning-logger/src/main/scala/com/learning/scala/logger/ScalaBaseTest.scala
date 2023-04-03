package com.learning.scala.logger

import com.learning.logger.BaseTest
import org.apache.logging.log4j.{LogManager, Logger}
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.{AfterAll, BeforeAll, TestInstance}

/**
 * @author over.li
 * @since 2023/3/17
 */
@TestInstance(Lifecycle.PER_CLASS)
class ScalaBaseTest extends BaseTest {

    val logger: Logger = LogManager.getLogger(this.getClass)

    def console[T](list:Array[T]): Unit = {
        logger.info("")
        list.foreach(v => {
            logger.info(s"$v")
        })
        logger.info("")
    }
}
