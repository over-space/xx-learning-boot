package com.learning.flink.sql

import com.learning.flink.FlinkBaseTest
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.junit.jupiter.api.Test

/**
 * @author over.li
 * @since 2023/7/14
 */
class FlinkSQLDDLTest extends FlinkBaseTest {

    @Test
    def testCreateTableByCSV(): Unit = {

        val env = getStreamExecutionEnvironment()

        env.setParallelism(1)

        val tableEnv = StreamTableEnvironment.create(env)

        val sql =
            """
              CREATE TABLE t_person_csv(
                id INT,
                name STRING,
                age INT
              )
              WITH(
                'connector'='filesystem',
                'path'='./src/test/resources/data/person.csv',
                'format'='csv'
              )
              """

        tableEnv.executeSql(sql)

        val sqlQuery = tableEnv.sqlQuery("select id, name, age from t_person_csv where age > 22")

        tableEnv.toDataStream(sqlQuery).print()

        env.execute("flink-sql-job")
    }

}
