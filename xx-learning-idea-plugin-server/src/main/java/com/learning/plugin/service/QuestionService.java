package com.learning.plugin.service;

import com.learning.plugin.controller.request.QuestionRequest;
import com.learning.plugin.entity.QuestionEntity;

public interface QuestionService {

    QuestionEntity findByBusinessId(Long businessId);

    QuestionEntity updateOrSave(QuestionRequest request);

    QuestionEntity random();
}
