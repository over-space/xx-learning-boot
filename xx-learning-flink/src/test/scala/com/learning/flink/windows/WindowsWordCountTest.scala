package com.learning.flink.windows

import com.learning.flink.FlinkBaseTest
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.api.common.functions.{AggregateFunction, ReduceFunction}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.{SlidingProcessingTimeWindows, TumblingEventTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import org.junit.jupiter.api.Test

import java.time.Duration


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
          .map(x => {
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
    def testEventTimeWindow(): Unit = {
        val env = getStreamExecutionEnvironment()
        env.setParallelism(1)
        val dsStream = env.fromElements(

            // username, url, time
            MyEvent("zs", "/user?time=1000", 1000L),
            MyEvent("zs", "/order?time=1500", 1500L),
            MyEvent("zs", "/product?id=1&time=2000", 2000L),
            MyEvent("zs", "/product?id=2&time=2300", 2300L),
            MyEvent("zs", "/product?id=3&time=1800", 1800L),
            // MyEvent("zs", "/product?id=5&time=4100", 4100L),
            // MyEvent("zs", "/product?id=5&time=5000", 5000L),
            // MyEvent("zs", "/product?id=5&time=7000", 7000L),
            // MyEvent("zs", "/product?id=5&time=9000", 9000L),

            MyEvent("ls", "/user?time=1000", 1000L),
            MyEvent("ls", "/order?time=1500", 1500L),
            MyEvent("ls", "/product?id=1&time=2000", 2000L),
            MyEvent("ls", "/product?id=2&time=2300", 2300L),
            MyEvent("ls", "/product?id=3&time=1800", 1800L),

            MyEvent("zs", "/product?id=3&time=1700", 1700L)

        )

        var outputTag = OutputTag[MyEvent]("late");

        // dsStream.assignTimestampsAndWatermarks(WatermarkStrategy.forBoundedOutOfOrderness[MyEvent](Duration.ZERO)
        val stream = dsStream.assignTimestampsAndWatermarks(WatermarkStrategy.forMonotonousTimestamps[MyEvent]()
          .withTimestampAssigner(new SerializableTimestampAssigner[MyEvent] {
              override def extractTimestamp(element: MyEvent, recordTimestamp: Long): Long = element.eventTime
          })
        ).keyBy(_.username)
          .window(TumblingEventTimeWindows.of(Time.seconds(2))) // 滚动时间窗口，窗口大小 2 秒。
          .allowedLateness(Time.seconds(0))
          .sideOutputLateData(outputTag)
          .reduce(new ReduceFunction[MyEvent] {
              override def reduce(value1: MyEvent, value2: MyEvent): MyEvent = {
                  MyEvent(value1.username, value1.url + "," + value2.url, 0)
              }
          }, new ProcessWindowFunction[MyEvent, MyEvent, String, TimeWindow] {
              override def process(key: String, context: Context, elements: Iterable[MyEvent], out: Collector[MyEvent]): Unit = {
                  for (elem <- elements) {
                      out.collect(elem)
                  }
                  println("key: " + key + "\twindows_time: " + context.window.getStart + " ~ " + context.window.getEnd)
              }
          })

        stream.print("nomal");
        stream.getSideOutput(outputTag).print("late");

        env.execute()
    }

    @Test
    def testOrderEventTimeWindow02(): Unit = {
        val env = getStreamExecutionEnvironment()

        env.setParallelism(1)

        var outputTag = OutputTag[MyEvent]("late");

        val stream = env.socketTextStream("127.0.0.1", 8088)
          .map(x => {
              val array = x.trim.split(" ")
              MyEvent(array(0).trim, array(1).trim, array(2).trim.toLong)
          })
          .assignTimestampsAndWatermarks(WatermarkStrategy.forMonotonousTimestamps[MyEvent]().withTimestampAssigner(new SerializableTimestampAssigner[MyEvent] {
              override def extractTimestamp(element: MyEvent, recordTimestamp: Long): Long = element.eventTime
          })) // 有序流
          .keyBy(_.username)
          .window(TumblingEventTimeWindows.of(Time.seconds(2))) // 滚动窗口 2 秒
          .sideOutputLateData(outputTag)
          .reduce(new ReduceFunction[MyEvent] {
              override def reduce(value1: MyEvent, value2: MyEvent): MyEvent = MyEvent(value1.username, value1.url + "," + value2.url, 0)
          }, new ProcessWindowFunction[MyEvent, MyEvent, String, TimeWindow] {
              override def process(key: String, context: Context, elements: Iterable[MyEvent], out: Collector[MyEvent]): Unit = {
                  println("key: " + key + "\twindows_time: " + context.window.getStart + " ~ " + context.window.getEnd)
                  for (elem <- elements) {
                      out.collect(elem)
                  }
                  println("-------------------------------------------------------------------------------------------")
              }
          })
        stream.print("normal");
        stream.getSideOutput(outputTag).print("late")
        env.execute("testEventTimeWindow02")
    }

    @Test
    def testNoOrderEventTimeWindow02(): Unit = {
        val env = getStreamExecutionEnvironment()

        env.setParallelism(1)

        // 侧输出流
        var outputTag = OutputTag[MyEvent]("late");

        val stream = env.socketTextStream("127.0.0.1", 8088)
          .map(x => {
              val array = x.trim.split(" ")
              MyEvent(array(0).trim, array(1).trim, array(2).trim.toLong)
          })
          // 无序流 延迟 1s 触发。
          .assignTimestampsAndWatermarks(WatermarkStrategy. forBoundedOutOfOrderness[MyEvent](Duration.ofSeconds(1))
            .withTimestampAssigner(new SerializableTimestampAssigner[MyEvent] {
              override def extractTimestamp(element: MyEvent, recordTimestamp: Long): Long = element.eventTime
          }))
          .keyBy(_.username)
          .window(TumblingEventTimeWindows.of(Time.seconds(2))) // 滚动窗口 2 秒
          .sideOutputLateData(outputTag)
          .reduce(new ReduceFunction[MyEvent] {
              override def reduce(value1: MyEvent, value2: MyEvent): MyEvent = MyEvent(value1.username, value1.url + "," + value2.url, 0)
          }, new ProcessWindowFunction[MyEvent, MyEvent, String, TimeWindow] {
              override def process(key: String, context: Context, elements: Iterable[MyEvent], out: Collector[MyEvent]): Unit = {
                  println("key: " + key + "\twindows_time: " + context.window.getStart + " ~ " + context.window.getEnd)
                  for (elem <- elements) {
                      out.collect(elem)
                  }
                  println("-------------------------------------------------------------------------------------------")
              }
          })
        stream.print("normal");
        stream.getSideOutput(outputTag).print("late")
        env.execute("testEventTimeWindow02")
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
                  for (eleme <- elements) {
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
