package com.learning.sharding;

import com.learning.logger.BaseTest;
import com.learning.sharding.entity.OrderEntity;
import com.learning.sharding.entity.OrderItemEntity;
import com.learning.sharding.entity.OrderLogEntity;
import com.learning.sharding.entity.OrderTypeEntity;
import com.learning.sharding.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

/**
 * @author over.li
 * @since 2022/9/2
 */
@SpringBootTest
public class ShardingSphereTest extends BaseTest {

    @Resource
    private OrderService orderService;


    @Test
    public void testDelete() {
        orderService.deleteAll();
    }

    @Test
    public void testSave() {
        for (int i = 1; i <= 100; i++) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(Long.valueOf(i));
            orderEntity.setOrderType(i % 3);
            orderEntity.setAmount(new Random().nextFloat() * 1000F);
            orderService.insert(orderEntity, null);

            // OrderItemEntity orderItemEntity = new OrderItemEntity();
            // orderItemEntity.setId(Long.valueOf(i));
            // orderItemEntity.setOrderType(i % 2);
            // orderItemEntity.setCreatedDate(LocalDateTime.now());
            // orderItemEntity.setName("测试-" + new Random().nextInt(1000));
            // orderService.insert(orderEntity, orderItemEntity);
        }
    }

    @Test
    public void testSaveOrderLog() {
        for (int i = 1; i <= 100; i++) {
            OrderLogEntity orderLogEntity = new OrderLogEntity();
            orderLogEntity.setOrderType(i);
            orderService.saveOrderLog(orderLogEntity);
        }
    }

    @Test
    public void testFindOrderLogList() {
        for (int i = 0; i < 10; i++) {
            orderService.findOrderLogList();
        }
    }

    @Test
    public void testDeleteAllOrderLog() {
        orderService.deleteAllOrderLog();
    }

    @Test
    public void testBroadcastTables() {
        for (int i = 1; i <= 100; i++) {
            OrderTypeEntity orderTypeEntity = new OrderTypeEntity();
            orderTypeEntity.setOrderType(i);
            orderService.saveOrderType(orderTypeEntity);
        }
    }
}
