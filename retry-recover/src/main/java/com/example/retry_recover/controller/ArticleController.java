package com.example.retry_recover.controller;

import com.example.retry_recover.dto.Article;
import com.example.retry_recover.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ArticleController {

    private final ArticleService dservice;
    private final Integer PORT = 11000;

    @PostMapping("/create-post")
    public void sendToApi(@RequestBody Article articleDto) throws Exception {
        dservice.sendToApi(articleDto,PORT);
    }

}