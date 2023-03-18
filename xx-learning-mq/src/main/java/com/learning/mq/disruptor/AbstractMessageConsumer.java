package com.learning.mq.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author over.li
 * @since 2022/10/9
 */
public abstract class AbstractMessageConsumer<T extends EventFactory> implements EventHandler<T> {

    private static final Logger logger = LogManager.getLogger(AbstractMessageConsumer.class);

    protected String consumerName;

    public AbstractMessageConsumer(String consumerName) {
        this.consumerName = consumerName;
    }

    @Override
    public void onEvent(T event, long sequence, boolean endOfBatch) throws Exception {
        logger.info("consumerName：{}, sequence: {}, event : {}, endOfBatch: {}", consumerName, sequence, event, endOfBatch);
    }
}
