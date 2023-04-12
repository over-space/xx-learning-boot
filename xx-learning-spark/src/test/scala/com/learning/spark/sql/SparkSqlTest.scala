package com.learning.spark.sql

import com.learning.logger.BaseTest.line
import com.learning.spark.SparkBaseTest
import org.apache.spark.sql.{DataFrame, SaveMode}
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

    @Test
    def testSparkSql02(): Unit = {
        val session = getSparkSession("spark-sql-02", false)

        val context = session.sparkContext
        context.setLogLevel("ERROR")

        import session.implicits._
        val dataDF:DataFrame = List("hello world", "hello spark", "hello java", "hello scala", "hello spark sql", "hello hadoop")
            .toDF("line")

        dataDF.createTempView("t_hello");

        session.sql("select * from t_hello").show()

        line()

        session.sql("select word,count(*) from (select explode(split(line, ' ')) as word from t_hello) as t group by t.word").show()

        line()

        val res = dataDF.selectExpr("explode(split(line, ' ')) as word").groupBy("word").count()

        // res.write.parquet("./logs/spark-sql/word")
        res.write.mode(SaveMode.Append).parquet("./logs/spark-sql/word")

        line()

        session.read.parquet("./logs/spark-sql/word").show()
    }

}
