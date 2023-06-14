package com.learning.flink.windows

import com.learning.flink.FlinkBaseTest
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.{SlidingProcessingTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import org.junit.jupiter.api.Test

/**
 * @author over.li
 * @since 2023/6/12
 */
class WindowsWordCountTest extends FlinkBaseTest {


  /**
   * 基于分组后的数据流上的时间滚动窗口
   */
  @Test
  def testKeyedByTumblingWindow01(): Unit = {
    val env = getStreamExecutionEnvironment()

    env.socketTextStream("127.0.0.1", 8088)
      .flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(_._1)
       .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
      // .sum(1)
      // .window(TumblingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(3))) // 宽口10s,偏移3秒
      .aggregate(new AggregateFunction[(String, Int), Int, Int] {

        override def createAccumulator(): Int = 0

        override def add(value: (String, Int), accumulator: Int): Int = value._2 + accumulator

        override def getResult(accumulator: Int): Int = accumulator

        override def merge(a: Int, b: Int): Int = a + b

      }, new ProcessWindowFunction[Int, (String, Int), String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[Int], out: Collector[(String, Int)]): Unit = {
          println("windows start: " + context.window.getStart, "\t end:" + context.window.getEnd)
          println("windows currentProcessingTime: " + context.currentProcessingTime)
          println("windows currentProcessingTime: " + System.currentTimeMillis())
          println("windows time: " + (context.window.getEnd - context.window.getStart))
          for (elem <- elements) {
            out.collect((key, elem))
          }
        }
      })
      .print()

    env.execute("KeyedByTumblingWindow01")
  }

  /**
   * 基于分组后的数据流上的时间滑动窗口
   */
  @Test
  def testKeyedBySlidingWindow02(): Unit = {
    val env = getStreamExecutionEnvironment()

    env.socketTextStream("127.0.0.1", 8088)
      .flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(_._1)
      .window(SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(3))) // 每3秒统计过去10秒的数据
      .sum(1)
      .print();

    env.execute("KeyedByTumblingWindow01")
  }

  /**
   * 基于分组后的数据流上的时间滑动窗口
   */
  @Test
  def testKeyedBySlidingWindow03(): Unit = {
    val env = getStreamExecutionEnvironment()

    env.socketTextStream("127.0.0.1", 8088) // lisi 40
      .map( x => {
        val splits = x.split(" ")
        (splits(0), splits(1).toInt)
      })
      .keyBy(_._1)
      .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
      .aggregate(new AggregateFunction[(String, Int), (String, Int, Int), (String, Double)] {
        override def createAccumulator(): (String, Int, Int) = ("", 0, 0)

        override def add(value: (String, Int), accumulator: (String, Int, Int)): (String, Int, Int) = {
          (value._1, value._2 + accumulator._2, accumulator._3 + 1)
        }

        override def getResult(accumulator: (String, Int, Int)): (String, Double) = {
          (accumulator._1, accumulator._2.toDouble / accumulator._3)
        }

        override def merge(a: (String, Int, Int), b: (String, Int, Int)): (String, Int, Int) = {
          (a._1, a._2 + b._2, a._3 + b._3)
        }
      }, new ProcessWindowFunction[(String, Double), (String, Double), String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[(String, Double)], out: Collector[(String, Double)]): Unit = {
          for (elem <- elements) {
            println("key: " + key + "\t value: " + elem)
          }
        }
      })
      .print();

    env.execute("KeyedByTumblingWindow01")
  }

  @Test
  def testWindow01(): Unit = {

    val env = getStreamExecutionEnvironment()

    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)

    val stream = env.socketTextStream("127.0.0.1", 8088)

    stream.flatMap(x => x.split(" "))
      .map(x => (x, 1))
      .keyBy(x => x._1)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print()

    env.execute();
  }

  @Test
  def testWindow02(): Unit = {

    val env = getStreamExecutionEnvironment()

    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)

    val stream = env.socketTextStream("127.0.0.1", 8088)

    stream.flatMap(x => x.split(" "))
      .map(x => (x, 1))
      .keyBy(x => x._1)
      // .timeWindow(Time.seconds(10)) // 滚动窗口，每10秒开启一个新窗口。
      .timeWindow(Time.seconds(10), Time.seconds(5)) // 每5秒统计最近10秒的数据
      .aggregate(new AggregateFunction[(String, Int), Int, Int] {
        override def createAccumulator(): Int = 0

        override def add(value: (String, Int), accumulator: Int): Int = value._2 + accumulator

        override def getResult(accumulator: Int): Int = accumulator

        override def merge(a: Int, b: Int): Int = a + b
      }, new ProcessWindowFunction[Int, (String, Int), String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[Int], out: Collector[(String, Int)]): Unit = {
          println("windows start: " + context.window.getStart, "\t end:" + context.window.getEnd)
          for (eleme <- elements){
            out.collect((key, eleme))
          }
        }
      })
      .print()

    env.execute();
  }

  @Test
  def testWindow03(): Unit = {
    val env = getStreamExecutionEnvironment()
    val stream = env.socketTextStream("127.0.0.1", 8088)

    stream.flatMap(x => x.split(" "))
      .map(x => (x, 1))
      .keyBy(x => x._1)
      .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
      .aggregate(new AggregateFunction[(String, Int), Int, Int] {
        override def createAccumulator(): Int = 0

        override def add(value: (String, Int), accumulator: Int): Int = value._2 + accumulator

        override def getResult(accumulator: Int): Int = accumulator

        override def merge(a: Int, b: Int): Int = a + b
      }, new ProcessWindowFunction[Int, (String, Int), String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[Int], out: Collector[(String, Int)]): Unit = {
          println("windows start: " + context.window.getStart, "\t end:" + context.window.getEnd)
          for (eleme <- elements) {
            out.collect((key, eleme))
          }
        }
      }).print()

    env.execute("Tumbling Processing Time Windows, WordCount");
  }
}
