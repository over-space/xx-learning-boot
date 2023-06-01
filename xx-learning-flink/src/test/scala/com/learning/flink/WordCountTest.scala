package com.learning.flink

import com.learning.logger.BaseTest
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.{Configuration, RestOptions}
import org.apache.flink.streaming.api.functions.co.CoMapFunction
import org.junit.jupiter.api.Test
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * @author over.li
 * @since 2023/5/25
 */
class WordCountTest extends BaseTest{

    @Test
    def testWordCountBySocket01(): Unit = {

        val env = StreamExecutionEnvironment.getExecutionEnvironment

        val socketDS:DataStream[String] = env.socketTextStream("127.0.0.1", 8088)

        val wordDS:DataStream[String] = socketDS.flatMap(_.split(" "))

        val pairDS:DataStream[(String, Int)] = wordDS.map((_, 1))

        val keyDS = pairDS.keyBy(x => x._1)

        val result = keyDS.sum(1).setParallelism(2)

        result.print()

        env.execute("flink-stream-work-count-01");
    }

    @Test
    def testWordCountBySocket02(): Unit = {

        val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI()

        val socketDS: DataStream[String] = env.socketTextStream("127.0.0.1", 8088)

        val wordDS: DataStream[String] = socketDS.flatMap(_.split(" "))

        val pairDS: DataStream[(String, Int)] = wordDS.map((_, 1))

        val keyDS = pairDS.keyBy(x => x._1)

        val result = keyDS.sum(1)

        result.print()

        env.execute("flink-stream-work-count-02");
    }

    @Test
    def testWordCountBySocket03(): Unit = {

        val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI()

        val stream: DataStream[Long] = env.generateSequence(1, 100)

        stream.map(x => (x % 3, 1))
            .keyBy(new KeySelector[(Long, Int), Long] {
                override def getKey(value: (Long, Int)): Long = value._1;
            })
            .sum(1)
            .print();

        env.execute("flink-stream-work-count-03");
    }

    @Test
    def testWordCountBySocket04(): Unit = {

        val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI()

        val ds1: DataStream[String] = env.socketTextStream("127.0.0.1", 8088)
        val ds2: DataStream[String] = env.socketTextStream("127.0.0.1", 8099)

        val ds1Value = ds1.flatMap(_.split(" ")).map(x => (x, 1)).keyBy(x => x._1).sum(1)
        val ds2Value = ds2.flatMap(_.split(" ")).map(x => (x, 1)).keyBy(x => x._1).sum(1)

        // 合并在一起处理，数据没有真实合并，数据类型可以不一致。
        val connect = ds1Value.connect(ds2Value)

        connect.map(new CoMapFunction[(String, Int), (String, Int), (String, Int)] {
            override def map1(value: (String, Int)): (String, Int) = ("map1:" + value._1, value._2 + 50)

            override def map2(value: (String, Int)): (String, Int) = ("map2:" + value._1, value._2 + 100)
        }).print();

        env.execute("flink-stream-work-count-04");
    }

    @Test
    def testWordCountBySocket05(): Unit = {

        val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI()

        val ds1: DataStream[String] = env.socketTextStream("127.0.0.1", 8088)
        val ds2: DataStream[String] = env.socketTextStream("127.0.0.1", 8099)

        val ds1Value = ds1.flatMap(_.split(" ")).map(x => (x, 1)).keyBy(x => x._1).sum(1)
        val ds2Value = ds2.flatMap(_.split(" ")).map(x => (x, 1)).keyBy(x => x._1).sum(1)

        // 合并在一起处理，数据真实合并,数据类型需要保持一致
        val connect = ds1Value.union(ds2Value)

        connect.keyBy(x => x._1).sum(1).print();

        env.execute("flink-stream-work-count-05");
    }


    @Test
    def testWordCountByFile(): Unit = {
        val env = ExecutionEnvironment.getExecutionEnvironment

        val fileDS = env.readTextFile("src/test/resources/data/dict.txt")

        val wordDS = fileDS.flatMap(x => x.split(" "))

        val pairDS = wordDS.map(x => (x, 1))

        val groupDS = pairDS.groupBy(0)

        val result = groupDS.sum(1).setParallelism(2)

        result.print()
    }
}
