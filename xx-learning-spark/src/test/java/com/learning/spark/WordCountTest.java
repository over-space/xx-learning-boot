package com.learning.spark;

import com.learning.logger.BaseTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.junit.jupiter.api.Test;
import scala.Tuple2;

import java.util.Arrays;

public class WordCountTest extends BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);


    @Test
    void testWordCount01(){

        SparkConf conf = new SparkConf();
        conf.setAppName("word-count-01");
        conf.setMaster("local");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> fileRDD = context.textFile("/Users/flipos/Desktop/workspace/xx-learning-boot/xx-learning-spark/src/test/resources/data/work-count.txt");

        JavaRDD<String> wordRDD = fileRDD.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(StringUtils.split(line, "_")).iterator());

        JavaPairRDD<String, Integer> pairWordRDD = wordRDD.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));

        pairWordRDD.foreach((VoidFunction<Tuple2<String, Integer>>) tuple2 -> {
            logger.info("{}、{}", tuple2._1, tuple2._2);
        });

    }
}
