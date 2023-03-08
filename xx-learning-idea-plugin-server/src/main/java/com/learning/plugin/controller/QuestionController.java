package com.learning.plugin.controller;

import com.alibaba.fastjson2.JSON;
import com.learning.plugin.controller.request.ResponseResult;
import com.learning.plugin.entity.QuestionEntity;
import com.learning.plugin.entity.QuestionTypeEnum;
import com.learning.plugin.service.QuestionService;
import org.h2.util.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/question/findByBusinessId")
    public ResponseEntity<ResponseResult> findByBusinessId(@RequestParam Long businessId){
        QuestionEntity questionEntity = questionService.findByBusinessId(businessId);
        return ResponseEntity.ok(ResponseResult.success(questionEntity));
    }

    @GetMapping("/question/random")
    public ResponseEntity<ResponseResult> random(){
        QuestionEntity questionEntity = questionService.random();
        return ResponseEntity.ok(ResponseResult.success(questionEntity));
    }

    @PostMapping("/question/save")
    public ResponseEntity<ResponseResult> save(@RequestBody QuestionEntity questionEntity){
        questionService.save(questionEntity);
        return ResponseEntity.ok(ResponseResult.success());
    }

    public static void main(String[] args) {
        String content = "" +
                "1. 基于内存的数据结构：Redis 数据库是一个基于内存的数据存储系统，这意味着数据存储在内存中，而不是在磁盘上。由于内存访问速度比磁盘快得多，所以 Redis 能够快速地读取和写入数据。\n" +
                "2. 单线程模型：Redis 是单线程的，它的所有操作都是在单个线程中执行的。这使得 Redis 能够避免由于多线程引起的锁竞争和上下文切换的开销，从而提高了性能。\n" +
                "3. 非阻塞 I/O：Redis 使用非阻塞 I/O 操作来处理网络请求，这允许它同时处理多个客户端请求而无需创建多个线程。\n" +
                "4. 压缩存储：Redis 对于存储的数据进行了压缩，从而减少了内存的使用量。这也意味着 Redis 可以存储更多的数据在可用内存中，从而提高了性能。\n" +
                "5. 预分配内存：Redis 预分配内存以避免频繁地重新分配内存，这可以减少内存分配和释放的开销。\n" +
                "6. 持久化：Redis 提供了多种持久化选项，包括快照和日志文件，这使得它能够快速地恢复数据，并在必要时持久化数据。\n" +
                "综上所述，Redis 通过使用内存存储和单线程模型、非阻塞 I/O、压缩存储和预分配内存等技术，以及提供持久化选项，从而实现了出色的性能表现。";

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle("Redis为什么这么快？");
        questionEntity.setQuestionType(QuestionTypeEnum.MARKDOWN);
        questionEntity.setContent(content);
        System.out.println(JSON.toJSONString(questionEntity));
    }
}
