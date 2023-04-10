package com.learning.spark.sql

import com.learning.logger.BaseTest.line
import com.learning.spark.SparkBaseTest
import org.junit.jupiter.api.Test

/**
 * @author over.li
 * @since 2023/4/5
 */
class SparkSqlTest extends SparkBaseTest{

    @Test
    def testSparkSql01(): Unit = {
        val session = getSparkSession("spark-sql-01", false)

        val context = session.sparkContext
        context.setLogLevel("ERROR")

        val databases = session.catalog.listDatabases()
        databases.show()

        val tables = session.catalog.listTables()
        tables.show()

        val functions = session.catalog.listFunctions()
        functions.show()

        line()

        val frame = session.read.json("src/test/resources/data/person.json")
        frame.show()
        frame.printSchema()

        line()

        frame.createTempView("t_person")

        val nameFrame = session.sql("select name from t_person order by age desc")
        nameFrame.show()
        nameFrame.printSchema()

        // while (true){
        //     val sql = StdIn.readLine("input spark sql:")
        //     session.sql(sql).show()
        // }
    }

}
