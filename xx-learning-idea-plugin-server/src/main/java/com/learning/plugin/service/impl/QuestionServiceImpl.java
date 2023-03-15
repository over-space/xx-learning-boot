package com.learning.plugin.service.impl;

import com.learning.plugin.controller.request.QuestionRequest;
import com.learning.plugin.entity.QuestionEntity;
import com.learning.plugin.repository.QuestionRepository;
import com.learning.plugin.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionRepository questionRepository;

    @Override
    public QuestionEntity findByBusinessId(Long businessId) {
        return questionRepository.findByBusinessId(businessId);
    }


    @Override
    @Transactional
    public QuestionEntity updateOrSave(QuestionRequest request) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setBusinessId(request.getBusinessId());
        questionEntity.setQuestionType(request.getQuestionType());
        questionEntity.setTitle(request.getTitle());
        questionEntity.setContent(request.getContent());
        return questionRepository.updateOrSave(questionEntity);
    }

    @Override
    public QuestionEntity random() {
        return questionRepository.findOneByRandom();
    }

    private QuestionEntity save(QuestionRequest request){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(request.getContent());
        questionEntity.setTitle(request.getTitle());
        questionEntity.setQuestionType(request.getQuestionType());
        return questionRepository.save(questionEntity);
    }

    private QuestionEntity update(QuestionRequest request) {
        return null;
    }
}
