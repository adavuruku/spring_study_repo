package com.example.retry_recover.repo;

import java.util.Optional;

import com.example.retry_recover.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ArticleRepository extends JpaRepository<ArticleEntity,Integer>{

    Optional<ArticleEntity> findByName(String name);

}