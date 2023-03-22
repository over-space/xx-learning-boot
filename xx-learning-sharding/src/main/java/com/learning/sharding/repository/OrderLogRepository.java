package com.learning.sharding.repository;

import com.learning.sharding.entity.OrderLogEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author over.li
 * @since 2022/9/2
 */
public interface OrderLogRepository extends CrudRepository<OrderLogEntity, Long> {
}
