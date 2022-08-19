package com.informatorio.finalproject.service;


import com.informatorio.finalproject.converter.ArticleConverter;
import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {


    private final ArticleRepository articleRepository;

    private final ArticleConverter articleConverter;

    private final SourceRepository sourceRepository;

    public ArticleService(ArticleRepository articleRepository,
                          ArticleConverter articleConverter,
                          SourceRepository sourceRepository) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.sourceRepository = sourceRepository;
    }

    public CustomPage getArticles(int page, String q){
        PageRequest pageable = PageRequest.of(page, 5);
        Page<Article> pageResult;
        if (!q.isBlank()){
            pageResult = articleRepository.findByTitleContainingOrDescriptionContainingOrContentContainingOrAuthorFullNameContainingAndPublishedAtIsNotNull(pageable, q, q, q, q);
        }else{
            pageResult = articleRepository.findAll(pageable);
        }

        CustomPage customPage = new CustomPage();
        customPage.setContent(pageResult.getContent().stream()
                .map(articleConverter::toDto)
                .collect(Collectors.toList()));
        customPage.setTotalElements(pageResult.getTotalElements());
        customPage.setTotalPages(pageResult.getTotalPages());
        customPage.setSize(pageResult.getSize());
        customPage.setPageNumber(pageResult.getNumber());


        return customPage;
    }

    public Article getArticle(Long id){
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    public Article createArticle(ArticleDTO articleDTO){

        Article article = articleConverter.toEntity(articleDTO);

        List<Source> sources = articleDTO.getSources().stream()
                .map(id -> sourceRepository.findById(id.getId()))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());

        for(Source source : sources){
            article.addSource(source);
        }
        return articleRepository.save(article);

    }

    public Article updateArticle(ArticleDTO articleDTO, Long id){
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()){
            Article articleAux = article.get();
            articleAux.setTitle(articleDTO.getTitle());
            articleAux.setDescription(articleDTO.getDescription());
            articleAux.setContent(articleDTO.getContent());
            articleAux.setUrl(articleDTO.getUrl());
            articleAux.setUrlToImage(articleDTO.getUrlToImage());
            articleAux.setPublishedAt(articleDTO.getPublishedAt());
            return articleRepository.save(articleAux);

        }

        return null;
    }

    public boolean deleteArticle(Long id){
        Optional<Article> article = articleRepository.findById(id);
        if(article.isPresent()){
            for (Source source :  article.get().getSources()){
                article.get().getSources().remove(source);
            }
            articleRepository.delete(article.get());
            return true;
        }

        return false;
    }


}




