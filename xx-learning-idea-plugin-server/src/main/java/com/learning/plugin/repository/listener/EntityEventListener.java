package com.learning.plugin.repository.listener;

import com.learning.plugin.entity.BaseEntity;
import com.learning.plugin.util.ObjectIdUtil;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author over.li
 * @since 2023/3/13
 */
@Component
public class EntityEventListener{

    @PostPersist
    @PrePersist
    public void onPostInsert(BaseEntity entity) {
        entity.setBusinessId(ObjectIdUtil.objId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
    }

    @PostUpdate
    @PreUpdate
    public void onPostUpdate(BaseEntity entity) {
        entity.setUpdateTime(LocalDateTime.now());
    }
}
