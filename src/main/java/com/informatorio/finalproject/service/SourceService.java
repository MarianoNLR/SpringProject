package com.informatorio.finalproject.service;

import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.converter.SourceConverter;
import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SourceService {

    private SourceRepository sourceRepository;

    private final AuthorRepository authorRepository;

    private final AuthorConverter authorConverter;

    private final SourceConverter sourceConverter;
    private ArticleRepository articleRepository;


    public SourceService(SourceRepository sourceRepository, AuthorRepository authorRepository, AuthorConverter authorConverter, SourceConverter sourceConverter, ArticleRepository articleRepository) {
        this.sourceRepository = sourceRepository;
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
        this.sourceConverter = sourceConverter;
        this.articleRepository = articleRepository;
    }



    public boolean saveSource(SourceDTO sourceDTO){
        this.setSourceCode(sourceDTO);

        Source source = sourceConverter.toEntity(sourceDTO);
        sourceRepository.save(source);
        return true;
    }

    public boolean deleteSource(Long id){
        Source source = sourceRepository.findById(id).get();
        for (Article article : source.getArticles()){
            article.getSources().remove(source);
        }
        sourceRepository.deleteById(id);
        return true;
    }


    public SourceDTO updateSource(SourceDTO sourceDTO, Long id){
        Optional<Source> source = sourceRepository.findById(id);

        if (source.isPresent()){
            Source sourceAux = source.get();
            sourceAux.setName(sourceDTO.getName());
            this.setSourceCode(sourceDTO);
            sourceAux.setCode(sourceDTO.getCode());
            sourceRepository.save(sourceAux);
            return sourceConverter.toDto(sourceAux);
        }
        return null;
    }

    public CustomPage getAllSources(int page, String name){

        PageRequest pageable = PageRequest.of(page, 3);
        Page<Source> pageResult;
        if(!name.isBlank()){
            pageResult = sourceRepository.findByNameContaining(pageable, name);
        }else{
            pageResult = sourceRepository.findAll(pageable);
        }

        CustomPage customPage = new CustomPage();
        customPage.setContent(pageResult.getContent().stream()
                .map(source -> sourceConverter.toDto(source))
                .collect(Collectors.toList()));
        customPage.setTotalElements(pageResult.getTotalElements());
        customPage.setTotalPages(pageResult.getTotalPages());
        customPage.setSize(pageResult.getSize());
        customPage.setPageNumber(pageResult.getNumber());

        return customPage;
    }

    private void setSourceCode(SourceDTO sourceDTO){
        sourceDTO.setCode(sourceDTO.getName().toLowerCase().replace(" ", "-"));
    }
}
