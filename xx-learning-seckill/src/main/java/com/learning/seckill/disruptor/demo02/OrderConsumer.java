package com.learning.seckill.disruptor.demo02;

import com.learning.seckill.disruptor.AbstractMessageConsumer;
import org.slf4j.Logger;

public class OrderConsumer extends AbstractMessageConsumer<OrderEvent> {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(OrderConsumer.class);

    public OrderConsumer(String consumerName) {
        super(consumerName);
    }

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        super.onEvent(event, sequence, endOfBatch);

        // 业务处理。
        Thread.sleep(100);
    }
}
