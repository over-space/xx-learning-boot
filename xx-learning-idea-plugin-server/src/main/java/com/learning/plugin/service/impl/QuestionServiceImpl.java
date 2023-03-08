package com.learning.plugin.service.impl;

import com.learning.plugin.entity.QuestionEntity;
import com.learning.plugin.repository.QuestionRepository;
import com.learning.plugin.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionRepository questionRepository;

    @Override
    public QuestionEntity findByBusinessId(Long businessId) {
        return questionRepository.findByBusinessId(businessId);
    }


    @Override
    public QuestionEntity save(QuestionEntity questionEntity) {
        if(questionEntity.getBusinessId() == null){
            questionEntity.setBusinessId(System.nanoTime());
            questionEntity.setCreateTime(LocalDateTime.now());
            questionEntity.setUpdateTime(LocalDateTime.now());
        }
        return questionRepository.save(questionEntity);
    }

    @Override
    public QuestionEntity random() {
        return questionRepository.findOneByRandom();
    }
}
