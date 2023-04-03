package com.learning.spark

import org.apache.spark.rdd.RDD
import org.junit.jupiter.api.Test

/**
 * @author over.li
 * @since 2023/4/3
 */
class ScoreTest extends SparkBaseTest{

    @Test
    def testScore01(): Unit = {

        // 班级 姓名 年龄 性别 科目 成绩

        val context = getSparkContext("app-scala-score-01")

        val fileRDD = context.textFile("src/test/resources/data/score.txt")

        // 一共有多少个小于20岁的人参加考试？
        val lq20Count = fileRDD.map(_.split(" ")).filter(x => x(2).toInt < 20).groupBy(x => x(1)).count()
        print(s"一共有多少个小于20岁的人参加考试: $lq20Count")

        // 一共有多少个等于20岁的人参加考试？
        val eq20Count = fileRDD.map(_.split(" ")).filter(x => x(2).toInt == 20).groupBy(x => x(1)).count()
        print(s"一共有多少个等于20岁的人参加考试: $eq20Count")

        // 一共有多少个大于20岁的人参加考试？
        val gl20Count = fileRDD.map(_.split(" ")).filter(x => x(2).toInt > 20).groupBy(x => x(1)).count()
        print(s"一共有多少个大于20岁的人参加考试: $gl20Count")

        // 一共有多个男生参加考试？
        val nanCount = fileRDD.map(_.split(" ")).filter(x => x(3).equals("男")).groupBy(x => x(1)).count()
        print(s"一共有多个男生参加考试: $nanCount")

        // 一共有多个女生参加考试？
        val nvCount = fileRDD.map(_.split(" ")).filter(x => x(3).equals("女")).groupBy(x => x(1)).count()
        print(s"一共有多个女生参加考试: $nvCount")

        // 12班有多少人参加考试？
        val b12Count = fileRDD.map(_.split(" ")).filter(x => x(0).toInt == 12).groupBy(x => x(1)).count()
        print(s"12班有多少人参加考试: $b12Count")

        // 13班有多少人参加考试？
        val b13Count = fileRDD.map(_.split(" ")).filter(x => x(0).toInt == 13).groupBy(x => x(1)).count()
        print(s"13班有多少人参加考试: $b13Count")

        // 语文科目的平均成绩是多少？
        val cnMean = fileRDD.map(_.split(" ")).filter(x => x(4).equals("chinese"))
            .map(arr => arr(5).toInt)
            .mean()
        print(s"语文科目的平均成绩是多少: $cnMean")

        // 英语科目的平均成绩是多少？
        val engMean = fileRDD.map(_.split(" ")).filter(x => x(4).equals("english"))
            .map(arr => arr(5).toInt)
            .mean()
        print(s"英语科目的平均成绩是多少: $engMean")

        // 每个人平均成绩是多少？
        val personAVG = fileRDD.map(_.split(" "))
            .map(x => (x(1), x(5).toInt))
            .groupByKey()
            .map(x => (x._1, x._2.sum / x._2.size))
        print(s"英语科目的平均成绩是多少: ")
        console(personAVG)

        // 12班平均成绩是多少？
        val person12Mean= fileRDD.map(_.split(" "))
            .filter(x => x(0).toInt == 12)
            .map(x => x(5).toInt)
            .mean()
        print(s"12班平均成绩是多少: " + person12Mean)

        // 12班男生平均总成绩是多少？165.0
        val person12NanMean = fileRDD.map(_.split(" "))
            .filter(x => x(0).toInt == 12 && x(3).equals("男"))
            .map(x => (x(1), x(5).toInt))
            .groupByKey()
            .map(x => (x._1, x._2.sum))
            .map(x => x._2)
            .mean()
        print(s"12班男生平均总成绩是多少: " + person12NanMean)

        // 12班女生平均总成绩是多少？210.0
        val person12NvMean = fileRDD.map(_.split(" "))
            .filter(x => x(0).toInt == 12 && x(3).equals("女"))
            .map(x => (x(1), x(5).toInt))
            .groupByKey()
            .map(x => (x._1, x._2.sum))
            .map(x => x._2)
            .mean()
        print(s"12班女生平均总成绩是多少: " + person12NvMean)

        // 13班平均成绩是多少？
        val total13Mean = fileRDD.map(_.split(" "))
            .filter(x => x(0).toInt == 13)
            .map(x => (x(1), x(5).toInt))
            .groupByKey()
            .map(x => (x._1, x._2.sum))
            .map(x => x._2)
            .mean()
        print(s"13班平均成绩是多少: " + total13Mean)

        // 全校语文成绩最高分是多少？
        val max = fileRDD.map(_.split(" "))
            .filter(x => x(4).equals("chinese"))
            .map(x => x(5).toInt)
            .max()
        print(s"全校语文成绩最高分是多少: " + max)

        // 12班语文成绩最低分是多少？50
        val min = fileRDD.map(_.split(" "))
            .filter(x => x(0).toInt == 12)
            .filter(x => x(4).equals("chinese"))
            .map(x => x(5).toInt)
            .min()
        print(s"12班语文成绩最低分是多少: " + min)

        // 总成绩大于150分的12班的女生有几个
        val count = fileRDD.map(_.split(" "))
            .filter(x => x(0).toInt == 12 && x(3).equals("女"))
            .map(x => (x(1), x(5).toInt))
            .groupByKey()
            .filter(x => x._2.sum > 150)
            .count()
        print(s"总成绩大于150分的12班的女生有几个: " + count)

        // 班级 姓名 年龄 性别 科目 成绩
        // 总成绩大于150分，且数学大于等于70，且年龄大于等于19岁的学生的平均成绩是多少？
        val complex1 = fileRDD.map(x => {val line = x.split(" "); (line(0)+","+line(1)+","+line(3),line(5).toInt)})
        val complex2 = fileRDD.map(x => {val line = x.split(" "); (line(0)+","+line(1)+","+line(2)+","+line(3)+","+line(4),line(5).toInt)})

        // 过滤出总分大于150的,并求出平均成绩
        val com1: RDD[(String, Int)] = complex1.map(x=>(x._1,(x._2,1)))
            .reduceByKey((a, b)=>(a._1+b._1,a._2+b._2))
            .filter(a=>(a._2._1>150))
            .map(t=>(t._1,t._2._1/t._2._2))

        // 过滤出 数学大于等于70，且年龄大于等于19岁的学生
        val com2: RDD[(String, Int)] = complex2.filter(a=>{val line = a._1.split(",");line(4).equals("math") && a._2>=70 && line(2).toInt>=19})
            .map(a=>{val line2 = a._1.split(",");(line2(0)+","+line2(1)+","+line2(3),a._2.toInt)})

        val result = com1.join(com2).map(a => (a._1, a._2._1))
        print(s"总成绩大于150分，且数学大于等于70，且年龄大于等于19岁的学生的平均成绩是多少: " )
        console(result)

    }

}
