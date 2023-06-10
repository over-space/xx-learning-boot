package com.learning.flink.transformation

import com.learning.logger.BaseTest
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, createTypeInformation}
import org.apache.flink.util.Collector
import org.junit.jupiter.api.Test

/**
 * @author over.li
 * @since 2023/6/9
 */
class ProcessFunctionTest extends BaseTest {

  @Test
  def test(): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream = env.socketTextStream("127.0.0.1", 8088)

    stream.map(x => {
      val splits = x.split(" ")
      (splits(0), splits(1).toLong)
    }).keyBy(x => x._1)
      .process(new KeyedProcessFunction[String, (String, Long), String] {

        override def processElement(value: (String, Long), ctx: KeyedProcessFunction[String, (String, Long), String]#Context, out: Collector[String]): Unit = {

          val current = ctx.timerService().currentProcessingTime()

          if (value._2 > 100) {
            // 2秒触发onTimer事件
            var timer = current + 2 * 1000;
            ctx.timerService().registerProcessingTimeTimer(timer)
          }
        }

        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, (String, Long), String]#OnTimerContext, out: Collector[String]): Unit = {

          var msg = ctx.getCurrentKey + ": 警告，警告，警告，已超速"

          out.collect(msg)

        }
      }).print()

    env.execute()
  }

}
