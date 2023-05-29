import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}

/**
 * @author over.li
 * @since 2023/5/26
 */
object WordCount {

    def main(args: Array[String]): Unit = {

        val env = StreamExecutionEnvironment.getExecutionEnvironment

        val socketDS: DataStream[String] = env.socketTextStream("127.0.0.1", 8088)

        val wordDS: DataStream[String] = socketDS.flatMap(_.split(" "))

        val pairDS: DataStream[(String, Int)] = wordDS.map((_, 1))

        val keyDS = pairDS.keyBy(0)

        val result = keyDS.sum(1).setParallelism(2)

        result.print()

        env.execute("flink-stream-work-count");

    }

}
