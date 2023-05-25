package com.learning.flink

import com.learning.logger.BaseTest
import org.apache.flink.api.scala.ExecutionEnvironment
import org.junit.jupiter.api.Test
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * @author over.li
 * @since 2023/5/25
 */
class WordCountTest extends BaseTest{

    @Test
    def testWordCountBySocket(): Unit = {

        val env = StreamExecutionEnvironment.getExecutionEnvironment

        val socketDS:DataStream[String] = env.socketTextStream("127.0.0.1", 8088)

        val wordDS:DataStream[String] = socketDS.flatMap(_.split(" "))

        val pairDS:DataStream[(String, Int)] = wordDS.map((_, 1))

        val keyDS = pairDS.keyBy(0)

        val result = keyDS.sum(1).setParallelism(2)

        result.print()

        env.execute("flink-stream-work-count");
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
