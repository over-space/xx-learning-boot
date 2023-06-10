package com.learning.flink

import com.learning.logger.BaseTest
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment}

/**
 * @author over.li
 * @since 2023/6/9
 */
class FlinkBaseTest extends BaseTest{


  def getStreamExecutionEnvironment(): StreamExecutionEnvironment = {
    StreamExecutionEnvironment.getExecutionEnvironment
  }

  def getLocalEnvironment(): StreamExecutionEnvironment = {
    StreamExecutionEnvironment.createLocalEnvironment()
  }
}
