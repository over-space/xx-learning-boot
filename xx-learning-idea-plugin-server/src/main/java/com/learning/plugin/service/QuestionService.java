package com.learning.plugin.service;

import com.learning.plugin.entity.QuestionEntity;

public interface QuestionService {

    QuestionEntity findByBusinessId(Long businessId);

    QuestionEntity save(QuestionEntity questionEntity);

    QuestionEntity random();
}
