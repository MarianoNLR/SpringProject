package com.informatorio.finalproject.controller;


import com.informatorio.finalproject.converter.ArticleConverter;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class ArticleController {

    private ArticleRepository articleRepository;

    private final ArticleConverter articleConverter;

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleConverter articleConverter, ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        //this.authorRepository = authorRepository;
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<?> getArticles(@RequestParam int page){
        PageRequest pageable = PageRequest.of(page, 3);
        Page<Article> pageResult = articleRepository.findAll(pageable);

        Map<String, Object> customPage = new HashMap<>();
        customPage.put("content", pageResult.getContent().stream()
                .map(article -> articleConverter.toDto(article))
                .collect(Collectors.toList()));
        customPage.put("page", pageResult.getNumber());
        customPage.put("size", pageResult.getSize());
        customPage.put("totalElements", pageResult.getTotalElements());

        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }
/*
    @PostMapping
    public ResponseEntity<Article>saveArticle(@Validated @RequestBody Article article){
        Optional<Author> authorOptional = authorRepository.findById(article.getAuthor().getId());

        if(!authorOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        article.setAuthor(authorOptional.get());
        Article savedArticle = articleRepository.save(article);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedArticle.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(savedArticle);
    }*/

    @GetMapping("articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id){
        Optional<Article> articleOptional = articleRepository.findById(id);
        //return ResponseEntity.ok(articleRepository.findById(id));
        return ResponseEntity.ok(articleOptional.get());
    }
}
