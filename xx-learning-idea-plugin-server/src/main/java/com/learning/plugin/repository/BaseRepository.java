package com.learning.plugin.repository;

import com.learning.springboot.entity.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    T findOneByBusinessId(Long businessId);

    default T updateOrSave(T entity){
        Long businessId = entity.getBusinessId();
        if(Objects.isNull(businessId)){
            return save(entity);
        }else{
            T dbEntity = (T) findOneByBusinessId(businessId);
            BeanUtils.copyProperties(entity, dbEntity, getBaseEntityFields());
            return save(dbEntity);
        }
    }

    default String[] getBaseEntityFields(){
        return Arrays.stream(BaseEntity.class.getDeclaredFields())
                .map(Field::getName)
                .toArray(String[]::new);
    }
}
