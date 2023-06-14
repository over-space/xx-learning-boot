package com.learning.flink.kafka

import com.learning.logger.BaseTest
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Test

import java.util.Properties
import scala.io.Source

/**
 * @author over.li
 * @since 2023/6/12
 */
class KafkaProducerTest extends BaseTest {


  @Test
  def product(): Unit = {
    var properties = new Properties()
    properties.setProperty("bootstrap.servers", "192.168.137.211:9092,192.168.137.212:9092,192.168.137.213:9092")
    properties.setProperty("key.serializer", classOf[StringSerializer].getName)
    properties.setProperty("value.serializer", classOf[StringSerializer].getName)

    val producer = new KafkaProducer[String, String](properties)

    val source = Source.fromFile("./src/test/resources/data/car.txt")
    val iterator = source.getLines()

    for (i <- 1 to 100) {
      for (elem <- iterator) {
        println(elem)
        producer.send(new ProducerRecord[String, String]("topic-car-list", "key-" + (i % 10), elem))
        Thread.sleep(300)
      }
    }
    source.close()
  }


}
