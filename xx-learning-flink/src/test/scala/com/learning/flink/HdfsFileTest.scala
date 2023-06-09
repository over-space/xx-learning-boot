package com.learning.flink

import com.learning.logger.BaseTest
import org.apache.flink.api.java.io.TextInputFormat
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.source.FileProcessingMode
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, createTypeInformation}

/**
 * @author over.li
 * @since 2023/6/1
 */
class HdfsFileTest extends BaseTest{

    def testHdfsFile01(): Unit = {

        val env = StreamExecutionEnvironment.getExecutionEnvironment

        var filePath = "hdfs://192.168.137.211:8020/var/bigdata/data/flink/"
        val textInputFormat = new TextInputFormat(new Path(filePath))
        val fileStream = env.readFile(textInputFormat, filePath, FileProcessingMode.PROCESS_CONTINUOUSLY, 10)

        fileStream.flatMap(_.split(" ")).map((_, 1)).keyBy(x => x._1).sum(1).print()

        env.execute("flink-hdfs-01")
    }

}
