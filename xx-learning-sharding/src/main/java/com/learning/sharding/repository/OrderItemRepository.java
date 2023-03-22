package com.learning.sharding.repository;

import com.learning.sharding.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author over.li
 * @since 2022/9/2
 */
public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Long> {
}
