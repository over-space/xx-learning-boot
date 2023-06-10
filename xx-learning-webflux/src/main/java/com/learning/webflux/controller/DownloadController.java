package com.learning.webflux.controller;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

/**
 * @author over.li
 * @since 2023/4/28
 */
@RestController
public class DownloadController {

    @GetMapping(value = "/download")
    public Mono<Void> download(ServerHttpResponse response) {
        File file = new File("/Users/flipos/Movies/Videos/流浪地球2.2023.HD.1080P.国语中英双字.mp4"); // 这里为了模拟流 而去读取本地文件
        try  {
            FileInputStream in = new FileInputStream(file);
            Flux<DataBuffer> dataBufferFlux = DataBufferUtils.readByteChannel(in::getChannel, new DefaultDataBufferFactory(), 4096);

            ZeroCopyHttpOutputMessage zeroCopyHttpOutputMessage = (ZeroCopyHttpOutputMessage) response;
            HttpHeaders headers = zeroCopyHttpOutputMessage.getHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=1.mp4");

            // vnd.ms-excel 可以替换为 octet-stream
            MediaType application = new MediaType("application", "octet-stream", Charset.forName("UTF-8"));
            headers.setContentType(application);
            return zeroCopyHttpOutputMessage.writeWith(dataBufferFlux);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

}
