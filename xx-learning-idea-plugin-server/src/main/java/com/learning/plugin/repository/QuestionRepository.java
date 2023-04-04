package com.learning.plugin.repository;

import com.learning.plugin.entity.QuestionEntity;
import com.learning.springboot.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends BaseRepository<QuestionEntity> {

    QuestionEntity findByBusinessId(Long businessId);

    @Query(nativeQuery = true, value = "SELECT t1.* FROM t_question AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM t_question)) AS id) AS t2 WHERE t1.id>=t2.id ORDER BY t1.id LIMIT 1")
    QuestionEntity findOneByRandom();

}
