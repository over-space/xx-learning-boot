package com.learning.springcloud.gateway.route;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author over.li
 * @since 2023/4/10
 */
@Component
public class RouteRefreshEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publishEvent;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publishEvent = applicationEventPublisher;
    }

    public void refresh() {
        publishEvent.publishEvent(new RefreshRoutesEvent(this));
    }
}
