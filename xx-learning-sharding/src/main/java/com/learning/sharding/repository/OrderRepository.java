package com.learning.sharding.repository;

import com.learning.sharding.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author over.li
 * @since 2022/9/2
 */
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
