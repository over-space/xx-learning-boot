package com.learning.plugin.entity;


import com.learning.springboot.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_question")
public class QuestionEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private QuestionTypeEnum questionType;
    private String title;

    @Column(columnDefinition = "text default null")
    private String content;

    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
