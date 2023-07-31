package com.learning.flink.cep

import com.learning.flink.FlinkBaseTest
import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.junit.jupiter.api.Test

import java.util

/**
 * @author over.li
 * @since 2023/7/17
 */
class LoginByCEPTest extends FlinkBaseTest {


    @Test
    def testCase01(): Unit = {
        val env = getStreamExecutionEnvironment()

        env.setParallelism(1)

        val dataStream = env.fromElements(
            new Event(1, "a"),
            new Event(2, "b"),
            new Event(1, "b1"),
            new Event(3, "b2"),
            new Event(4, "d"),
            new Event(4, "b3")
        )

        val start = Pattern.begin[Event]("start").where(_.name.startsWith("a"))

        val strict = start.next("next").where(_.name.startsWith("b"))

        val relaxed = start.followedBy("next").where(_.name.startsWith("b"))

        val patternStream = CEP.pattern(dataStream, relaxed)

        val result = patternStream.select(new EventMatch())

        // Print matches
        result.print("")

        env.execute("Flink CEP Contiguity Conditions, Simple Pattern Example")
    }

    class EventMatch extends PatternSelectFunction[(Event), String]{
        override def select(pattern: util.Map[String, util.List[Event]]): String = {
            val sb = new StringBuilder()
            if(pattern.get("start") != null){
                pattern.get("start").forEach(elme => {
                    println(elme.name)
                    sb.append(elme.name).append(" -> ")
                })
            }
            sb.toString()
        }
    }

    @Test
    def testLoginByCEP(): Unit = {

        val env = getStreamExecutionEnvironment()

        env.setParallelism(1)

        // 1、准备数据流
        val stream = env.fromElements(
            new LoginEvent(1, "张三", "fail", 1577080469000L),
            new LoginEvent(2, "张三", "fail", 1577080470000L),
            new LoginEvent(3, "张三", "fail", 1577080472000L),
            new LoginEvent(4, "李四", "fail", 1577080469000L),
            new LoginEvent(5, "李四", "succ", 1577080473000L),
            new LoginEvent(6, "张三", "fail", 1577080477000L)
        ).assignAscendingTimestamps(_.loginTime)

        // val pattern = Pattern.begin[LoginEvent]("start")
        //   .where(_.loginType.equals("fail"))
        //   .timesOrMore(3) // 至少 3 次
        //   .greedy
        //   .within(Time.seconds(10)) // 10秒内有效

        val pattern = Pattern.begin[LoginEvent]("start").where(_.loginType.equals("fail"))
          .followedBy("followedBy1").where(_.loginType.equals("fail"))
          .followedBy("followedBy2").where(_.loginType.equals("fail"))
          .timesOrMore(2)
          .within(Time.seconds(10))

        val patternStream = CEP.pattern(stream.keyBy(_.username), pattern)

        val result = patternStream.select(new LoginEventSelect())

        result.print()

        env.execute("flink cep job")
    }

    class LoginEventSelect extends PatternSelectFunction[LoginEvent, String]{
        override def select(pattern: util.Map[String, util.List[LoginEvent]]): String = {

            val events = pattern.get("start")

            val sb = new StringBuilder().append("用户名：").append(events.get(0).username).append("恶意登录，")

            for(i <- 0 until events.size()){
                val event = events.get(i)
                sb.append(s"第${i + 1}次登录失败，登录失败时间：").append(event.loginTime).append(";")
            }

            sb.toString()
        }
    }

}
