package com.example.retry_recover.service;

import java.time.LocalDateTime;

import com.example.retry_recover.dto.Article;
import com.example.retry_recover.entity.ArticleEntity;
import com.example.retry_recover.repo.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final RestClient.Builder restClient = RestClient.builder();
    private final ArticleRepository repo;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = (3000),multiplier = 2, maxDelay = 10000), recover = "storeInDb")
    public void sendToApi(Article articleDto, Integer PORT) {
        System.out.println("Retry Attempt at "+" "+LocalDateTime.now());
        restClient
                .build()
                .post()
                .uri("http://localhost:"+PORT+"/create-article")
                .body(articleDto)
                .retrieve()
                .onStatus(status -> status.isError(), (request, response)->{
                    System.out.println("Error occured!!!");
                })
                .onStatus(status -> !status.isError(), (req,res)->{
                    repo.deleteById(articleDto.getId());
                    System.out.println("SUCCESS");
                })
                .toBodilessEntity();

    }

    @Recover
    public void storeInDb(Article articleDto, Integer PORT) {
        System.out.println("Saving Failed Data into DB");
        ArticleEntity entity = new ArticleEntity(articleDto.getId(),articleDto.getName());
        if(repo.findByName(articleDto.getName()).isEmpty()) {
            repo.save(entity);
        }
        System.out.println("RECOVERD "+articleDto.toString());
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void sendFailedDataToApi() {
        send(0,5000);
    }

    public void send(int pageNo, int pageSize) {
        Page<ArticleEntity> page = repo.findAll(PageRequest.of(pageNo, pageSize));
        while(page.getSize() > 0) {
            page.stream().map(ele -> new Article(ele.getId(),ele.getName())).forEach(ele -> {
                this.sendToApi(ele, 11000);
            });
            page = repo.findAll(PageRequest.of(pageNo+1, pageSize));
        }
    }

}
