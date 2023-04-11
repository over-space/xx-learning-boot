package com.learning.seata.schedule;

import com.learning.seata.gateway.RouteRefreshEventPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author over.li
 * @since 2023/4/11
 */
@Component
public class RouteRefreshScheduleTask {

    private static final Logger logger = LogManager.getLogger(RouteRefreshScheduleTask.class);

    @Resource
    private RouteRefreshEventPublisher routeRefreshEventPublisher;

    @Scheduled(initialDelay = 1000 * 1, fixedDelay = 1000 * 5)
    public void refresh(){
        logger.info("schedule task refresh route...");
        routeRefreshEventPublisher.refresh();
    }
}
