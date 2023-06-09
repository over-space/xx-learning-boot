package com.learning.flink.partition

import com.learning.flink.FlinkBaseTest
import org.apache.flink.api.common.functions.Partitioner
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, createTypeInformation}
import org.junit.jupiter.api.Test

/**
 *
 * @author over.li
 * @since 2023/6/9
 */
class PartitionTest extends FlinkBaseTest{

  /**
   * shuffle分区策略
   * 分区元素随机均匀分发到下游分区，网络开销比较大
   */
  @Test
  def shuffle(): Unit = {

    val env = getLocalEnvironment()

    var stream = env.generateSequence(1, 10).setParallelism(1)

    println("parallelism : " + stream.parallelism)

    stream.shuffle.print()

    env.execute();
  }

  /**
   * rebalance分区策略
   * 轮询分区元素，均匀的将元素分发到下游分区，下游每个分区的数据比较均匀，在发生数据倾斜时非常
   * 有用，网络开销比较大
   */
  @Test
  def rebalance(): Unit = {

    val env = getLocalEnvironment()

    var stream = env.generateSequence(1, 100).setParallelism(1)

    println("parallelism : " + stream.parallelism)

    stream.rebalance.print()

    env.execute();
  }

  /**
   * 场景：减少分区 防止发生大量的网络传输 不会发生全量的重分区
   * 通过轮询分区元素，将一个元素集合从上游分区发送给下游分区，发送单位是集合，而不是一个个元素
   * 注意：rescale发生的是本地数据传输，而不需要通过网络传输数据，比如taskmanager的槽数。简单
   * 来说，上游的数据只会发送给本TaskManager中的下游
   */
  @Test
  def rescale(): Unit = {

    val env = getStreamExecutionEnvironment()

    var stream = env.generateSequence(1, 20).setParallelism(2)

    println("parallelism : " + stream.parallelism)

    stream.writeAsText("./logs/stream_rescale_01").setParallelism(2)

    stream.rescale.writeAsText("./logs/stream_rescale_02").setParallelism(4)

    env.execute();
  }

  /**
   * 上游中每一个元素内容广播到下游每一个分区中
   */
  @Test
  def broadcast(): Unit = {

    val env = getStreamExecutionEnvironment()

    var stream = env.generateSequence(1, 20).setParallelism(2)

    println("parallelism : " + stream.parallelism)

    stream.writeAsText("./logs/stream_broadcast_01").setParallelism(2)

    stream.broadcast.writeAsText("./logs/stream_broadcast_02").setParallelism(4)

    env.execute();
  }

  /**
   * 上游分区的数据只分发给下游的第一个分区
   */
  @Test
  def global(): Unit = {

    val env = getStreamExecutionEnvironment()

    var stream = env.generateSequence(1, 10).setParallelism(2)

    println("parallelism : " + stream.parallelism)

    stream.writeAsText("./logs/stream_global_01").setParallelism(2)

    stream.global.writeAsText("./logs/stream_global_02").setParallelism(4)

    env.execute();
  }

  /**
   * 场景：一对一的数据分发，map、flatMap、filter 等都是这种分区策略
   * 上游分区数据分发到下游对应分区中
   * 注意：必须保证上下游分区数（并行度）一致，不然会有如下异常:
   */
  @Test
  def forward(): Unit = {

    val env = getStreamExecutionEnvironment()

    var stream = env.generateSequence(1, 10).setParallelism(2)

    println("parallelism : " + stream.parallelism)

    stream.writeAsText("./logs/partition_forward_01").setParallelism(2)

    stream.forward.writeAsText("./logs/partition_forward_02").setParallelism(2)

    env.execute();
  }

  /**
   * 根据上游分区元素的Hash值与下游分区数取模计算出，将当前元素分发到下游哪一个分区
   */
  @Test
  def keyBy(): Unit = {

    val env = getStreamExecutionEnvironment()

    var stream = env.generateSequence(1, 10).setParallelism(2)

    println("parallelism : " + stream.parallelism)

    stream.writeAsText("./logs/partition_keyBy_01").setParallelism(2)

    stream.keyBy(x => x).writeAsText("./logs/partition_keyBy_02").setParallelism(4)

    env.execute();
  }


  /**
   * 自定义分区策略
   */
  @Test
  def custom(): Unit = {
    val env = getStreamExecutionEnvironment()

    env.setParallelism(2)

    var stream = env.generateSequence(1, 10)

    println("parallelism : " + stream.parallelism)

    stream.writeAsText("./logs/partition_custom_01")

    stream.partitionCustom(new CustomPartitioner(), x => x).writeAsText("./logs/partition_custom_02")

    env.execute();
  }

  class CustomPartitioner extends Partitioner[Long]{
    override def partition(key: Long, numPartitions: Int): Int = {
      key.toInt % numPartitions
    }
  }
}
