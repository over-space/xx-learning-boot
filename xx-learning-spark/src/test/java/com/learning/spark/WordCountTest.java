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

import java.net.URL;
import java.util.Arrays;

public class WordCountTest extends BaseTest{

    private static final Logger logger = LogManager.getLogger(WordCountTest.class);


    @Test
    void testWordCount01(){
        URL fileURL = this.getClass().getClassLoader().getResource("data/work-count.txt");
        URL hadoopURL = this.getClass().getClassLoader().getResource("hadoop-2.8.1");
        logger.info("fileURL:{}", fileURL);
        logger.info("hadoopURL:{}", hadoopURL);

        System.setProperty("HADOOP_HOME", hadoopURL.getPath());

        SparkConf conf = new SparkConf();
        conf.setAppName("word-count-01");
        conf.setMaster("local");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> fileRDD = context.textFile(fileURL.getPath());

        JavaRDD<String> wordRDD = fileRDD.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(StringUtils.split(line, " ")).iterator());

        JavaPairRDD<String, Integer> pairWordRDD = wordRDD.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));

        pairWordRDD.foreach((VoidFunction<Tuple2<String, Integer>>) tuple2 -> {
            logger.info("{}、{}", tuple2._1, tuple2._2);
        });
    }
}
