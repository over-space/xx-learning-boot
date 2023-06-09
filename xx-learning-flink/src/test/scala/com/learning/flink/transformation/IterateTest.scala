package com.learning.flink.transformation

import com.learning.logger.BaseTest
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, createTypeInformation}
import org.junit.jupiter.api.Test

/**
 * @author over.li
 * @since 2023/6/6
 */
class IterateTest extends BaseTest{

  @Test
  def test(): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val socketStream = env.socketTextStream("127.0.0.1", 8888)

    val numberStream = socketStream.map(x => x.toLong)

    numberStream.iterate(iter => {
      val iterationBody = iter.map(x => {
        println("map : " + x)
        if (x > 0) x - 1
        else x
      })
      (iterationBody.filter(x => x > 0), iterationBody.filter(x => x <= 0))
    }).print("finish")

    env.execute();
  }
}
