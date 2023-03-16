package com.learning.spark;

import com.google.common.collect.Lists;
import com.learning.logger.BaseTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.junit.jupiter.api.Test;
import scala.Tuple2;

import java.util.Arrays;

/**
 * @author over.li
 * @since 2023/3/16
 */
public class WordCountTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(WordCountTest.class);

    JavaSparkContext getSparkConf(String appName){
        SparkConf conf = new SparkConf();
        conf.setAppName(appName);
        conf.setMaster("local");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.setLogLevel("WARN");
        return context;
    }

    @Test
    void testWordCount(){
        String path = getResourcePath(this.getClass(), "data/dict.txt");
        logger.info("path: {}", path);

        JavaSparkContext context = getSparkConf("j-word-count-01");

        JavaRDD<String> rdd = context.textFile(path);

        JavaRDD<String> wordRDD = rdd.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(StringUtils.split(line, " ")).iterator());

        line();
        wordRDD.foreach((VoidFunction<String>) word -> {
            logger.info("{}", word);
        });

        JavaPairRDD<String, Integer> pairRDD = wordRDD.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));

        line();
        pairRDD.foreach((VoidFunction<Tuple2<String, Integer>>) tuple2 -> {
            logger.info("{}\t{}", tuple2._1, tuple2._2);
        });

        JavaPairRDD<String, Integer> result = pairRDD.reduceByKey((Function2<Integer, Integer, Integer>) (v1, v2) -> v1 + v2);

        line();
        result.foreach((VoidFunction<Tuple2<String, Integer>>) tuple2 -> {
            logger.info("{}\t{}", tuple2._1, tuple2._2);
        });
    }

    @Test
    void testUnion(){

        JavaSparkContext context = getSparkConf("j-spack-join-01");

        JavaRDD<Integer> listRDD1 = context.parallelize(Lists.newArrayList(1, 2, 3, 4, 5));
        JavaRDD<Integer> listRDD2 = context.parallelize(Lists.newArrayList(3, 4, 5, 6, 7));

        // 合并
        JavaRDD<Integer> unionRDD = listRDD1.union(listRDD2);
        unionRDD.foreach((VoidFunction<Integer>) num -> logger.info("union result : {}", num));
        line();

        // 交集
        JavaRDD<Integer> intersectionRDD = listRDD1.intersection(listRDD2);
        intersectionRDD.foreach((VoidFunction<Integer>) num -> logger.info("intersection result : {}", num));
        line();

        // 差集
        JavaRDD<Integer> subtractRDD = listRDD1.subtract(listRDD2);
        subtractRDD.foreach((VoidFunction<Integer>) num -> logger.info("subtract result : {}", num));
        line();

        // 笛卡尔积
        JavaPairRDD<Integer, Integer> cartesianRDD = listRDD1.cartesian(listRDD2);
        cartesianRDD.foreach((VoidFunction<Tuple2<Integer, Integer>>) tuple2 -> logger.info("cartesian result: {}、{}", tuple2._1, tuple2._2));

        line();
        line();

        JavaPairRDD pairRDD1 = context.parallelizePairs(Lists.newArrayList(new Tuple2("lisi", 30), new Tuple2("zhangsan", 20), new Tuple2("wangwu", 25), new Tuple2("zhaoliu", 32)));
        JavaPairRDD pairRDD2 = context.parallelizePairs(Lists.newArrayList(new Tuple2("jane", 22), new Tuple2("zhangsan", 28), new Tuple2("cat", 21), new Tuple2("jay", 30),new Tuple2("lisi", 30)));

        // join操作
        JavaPairRDD joinRDD = pairRDD1.join(pairRDD2);
        joinRDD.foreach((VoidFunction) o -> logger.info("join result : {}", o));
        line();

        JavaPairRDD fullJoinRDD = pairRDD1.fullOuterJoin(pairRDD2);
        fullJoinRDD.foreach((VoidFunction) o -> logger.info("full join result : {}", o));
        line();

        JavaPairRDD leftJoinRDD = pairRDD1.leftOuterJoin(pairRDD2);
        leftJoinRDD.foreach((VoidFunction) o -> logger.info("left join result : {}", o));
        line();

        JavaPairRDD rightJoinRDD = pairRDD1.rightOuterJoin(pairRDD2);
        rightJoinRDD.foreach((VoidFunction) o -> logger.info("right join result : {}", o));
        line();
        line();

        // cogroup操作
        // 相同的tuple2._1分组，value合并。
        JavaPairRDD cogroupRDD = pairRDD1.cogroup(pairRDD2);
        cogroupRDD.foreach((VoidFunction) o -> logger.info("cogroup result : {}", o));

    }

}
