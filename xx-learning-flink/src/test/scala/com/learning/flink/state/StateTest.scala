package com.learning.flink.state

import com.learning.flink.FlinkBaseTest
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.createTypeInformation
import org.junit.jupiter.api.Test

/**
 * @author over.li
 * @since 2023/6/9
 */
class StateTest extends FlinkBaseTest{


  @Test
  def valueState(): Unit = {

    val env = getStreamExecutionEnvironment()

    val stream = env.socketTextStream("127.0.0.1", 8088)

    stream.map(x => {
      val splits = x.split(" ")
      (splits(0), splits(1).toLong)
    }).keyBy(x => x._1)
      .map(new RichMapFunction[(String, Long), String] {

        var lastState: ValueState[Long] = _

        override def open(parameters: Configuration): Unit = {
          val desc = new ValueStateDescriptor[Long]("lastState", createTypeInformation[Long])
          lastState = getRuntimeContext.getState(desc)
        }

        override def map(value: (String, Long)): String = {
          val lastSpeed = lastState.value()
          lastState.update(value._2)
          if(lastSpeed != 0 && (value._2 - lastSpeed).abs > 30){
            "over speed " + value._1
          }else{
            value._1
          }
        }
      }).print()

    env.execute()
  }

//  case class Car(id:String, speed:Long)
}
