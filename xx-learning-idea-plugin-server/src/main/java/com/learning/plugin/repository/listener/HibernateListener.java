package com.learning.plugin.repository.listener;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author over.li
 * @since 2023/3/13
 */
// @Component
public class HibernateListener {

    // @PersistenceUnit
    // private EntityManagerFactory entityManagerFactory;

    // @Resource
    // private EntityEventListener entityEventListener;

    // @PostConstruct
    private void init() {
        // SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        // EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        // registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(entityEventListener);
        // registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(entityEventListener);
    }

}
