package com.learning.plugin.controller.request;

import com.learning.plugin.entity.QuestionTypeEnum;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author over.li
 * @since 2023/3/13
 */
public class QuestionRequest extends BaseRequest{

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
