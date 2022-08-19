package com.informatorio.finalproject.service;

import com.informatorio.finalproject.converter.SourceConverter;
import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.util.CustomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SourceService {

    private final SourceRepository sourceRepository;

    private final SourceConverter sourceConverter;

    @Autowired
    public SourceService(SourceRepository sourceRepository, SourceConverter sourceConverter) {
        this.sourceRepository = sourceRepository;
        this.sourceConverter = sourceConverter;
    }



    public Source saveSource(SourceDTO sourceDTO){
        this.setSourceCode(sourceDTO);

        Source source = sourceConverter.toEntity(sourceDTO);
        return sourceRepository.save(source);

    }

    public boolean deleteSource(Long id){
        Source source = sourceRepository.findById(id).get();
        for (Article article : source.getArticles()){
            article.getSources().remove(source);
        }
        sourceRepository.deleteById(id);
        return true;
    }


    public Source updateSource(SourceDTO sourceDTO, Long id){
        Optional<Source> source = sourceRepository.findById(id);

        if (source.isPresent()){
            Source sourceAux = source.get();
            sourceAux.setName(sourceDTO.getName());
            this.setSourceCode(sourceDTO);
            sourceAux.setCode(sourceDTO.getCode());
            return sourceRepository.save(sourceAux);

        }
        return null;
    }

    public CustomPage getAllSources(int page, String name){

        PageRequest pageable = PageRequest.of(page, 5);
        Page<Source> pageResult;
        if(!name.isBlank()){
            pageResult = sourceRepository.findByNameContaining(pageable, name);
        }else{
            pageResult = sourceRepository.findAll(pageable);
        }

        CustomPage customPage = new CustomPage();
        customPage.setContent(pageResult.getContent().stream()
                .map(sourceConverter::toDto)
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
