package com.learning.seata.repository;

import com.learning.seata.entity.OrderEntity;
import com.learning.springboot.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author over.li
 * @since 2023/4/4
 */

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity> {

}
