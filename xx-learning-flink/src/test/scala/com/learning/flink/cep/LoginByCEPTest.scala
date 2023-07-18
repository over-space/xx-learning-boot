package com.learning.flink.cep

import com.learning.flink.FlinkBaseTest
import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.scala._
import org.junit.jupiter.api.Test

import java.util

/**
 * @author over.li
 * @since 2023/7/17
 */
class LoginByCEPTest extends FlinkBaseTest {

    case class LoginEvent(id: Long, username: String, loginType: String, loginTime: Long)

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

        // env.setParallelism(1)
        //
        // //1、准备数据流
        // val stream = env.fromCollection(Array(
        //     new LoginEvent(1, "张三", "fail", 1577080469000L),
        //     new LoginEvent(2, "张三", "fail", 1577080470000L),
        //     new LoginEvent(3, "张三", "fail", 1577080472000L),
        //     new LoginEvent(4, "李四", "fail", 1577080469000L),
        //     new LoginEvent(5, "李四", "succ", 1577080473000L),
        //     new LoginEvent(6, "张三", "fail", 1577080477000L)
        // ))
        //
        //
        // val value = stream.assignAscendingTimestamps(event => event.loginTime)
        //
        // val pattern = Pattern.begin[LoginEvent]("start")
        //   .where(_.loginType.equals("fail"))
        //   .timesOrMore(3)
        //   .greedy
        //   .within(Time.seconds(10))
        //
        // val ps = CEP.pattern(value.keyBy(_.username), pattern)
        //
        // ps.select(patternSelectFunction => {
        //     val list = patternSelectFunction.get("start").get.toList
        //
        //     val sb = new StringBuilder()
        //     sb.append("用户名:").append(list(0).username).append(" 恶意登录，")
        //     for (i <- 0 until list.size) {
        //         sb.append(s"第${i + 1}次登录失败的时间是:").append(list(i).loginTime).append(" , ").append(s"id 是${list(i).id}")
        //     }
        //     sb.toString()
        // }).print()
        //
        // env.execute();
    }

}
