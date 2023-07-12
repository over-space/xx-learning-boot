package com.learning.flink.transformation

import com.learning.flink.FlinkBaseTest
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.createTypeInformation
import org.apache.flink.util.FileUtils
import org.junit.jupiter.api.Test

import java.util.{Timer, TimerTask}
import scala.collection.mutable
import scala.io.Source

/**
 * @author over.li
 * @since 2023/7/11
 */
class RichMapFunctionTest extends FlinkBaseTest{

    @Test
    def testCacheFile(): Unit = {

        val env = getStreamExecutionEnvironment()

        env.registerCachedFile("./src/test/resources/data/city-dict.txt", "city-dict")

        val socketStream = env.socketTextStream("127.0.0.1", 8088)

        socketStream.map(_.toInt)
          .map(new RichMapFunction[Int, String] {

              private final var cityDistMap = new mutable.HashMap[Int, String]()

              override def open(parameters: Configuration): Unit = {
                  val file = getRuntimeContext().getDistributedCache().getFile("city-dict")
                  val lines = FileUtils.readFileUtf8(file).split("\n")
                  for (line <- lines) {
                      val splits = line.split(" ")
                      cityDistMap.put(splits(0).toInt, splits(1))
                  }
              }

              override def map(value: Int): String = {
                  cityDistMap.getOrElse(value, "not found city");
              }
          }).print()

        env.execute("job")
    }

    @Test
    def testCacheFile02(): Unit = {

        val env = getStreamExecutionEnvironment()

        val socketStream = env.socketTextStream("127.0.0.1", 8088)

        socketStream.map(_.toInt)
          .map(new RichMapFunction[Int, String] {

              private final val cityDistMap = new mutable.HashMap[Int, String]()

              override def open(parameters: Configuration): Unit = {
                  loaderCity();
                  new Timer(true).schedule(new TimerTask {
                      override def run(): Unit = {
                          loaderCity()
                      }
                  }, 2 * 1000, 10 * 1000)
              }

              override def map(value: Int): String = {
                  cityDistMap.getOrElse(value, "not found city");
              }

              def loaderCity() = {
                  println("loader city data....")
                  val source = Source.fromFile("./src/test/resources/data/city-dict.txt", "UTF-8")
                  val lines = source.getLines()
                  for (line <- lines) {
                      val splits = line.split(" ")
                      cityDistMap.put(splits(0).toInt, splits(1))
                  }
              }

          }).print()

        env.execute("job")
    }

}
