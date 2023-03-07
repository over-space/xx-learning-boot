package com.learning.plugin.controller;

import com.learning.plugin.controller.request.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @GetMapping("/learning/question/test")
    public ResponseEntity<CommonResponse> test(){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData("ok");
        return ResponseEntity.ok(commonResponse);
    }

}
