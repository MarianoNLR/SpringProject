package com.informatorio.finalproject.service;

import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.converter.SourceConverter;
import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.repository.SourceRepository;
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



    public SourceService(SourceRepository sourceRepository, AuthorRepository authorRepository, AuthorConverter authorConverter, SourceConverter sourceConverter) {
        this.sourceRepository = sourceRepository;
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
        this.sourceConverter = sourceConverter;
    }



    public boolean saveSource(SourceDTO sourceDTO){
        this.setSourceCode(sourceDTO);

        Source source = sourceConverter.toEntity(sourceDTO);
        sourceRepository.save(source);
        return true;
    }


    public Source updateSource(SourceDTO sourceDTO){
        this.setSourceCode(sourceDTO);
        Source source = sourceConverter.toEntity(sourceDTO);
        sourceRepository.save(source);
        return source;
    }

    public Map<String ,Object> getAllSources(int page, String name){

        PageRequest pageable = PageRequest.of(page, 3);
        Page<Source> pageResult;
        if(!name.isBlank()){
            pageResult = sourceRepository.findByNameContaining(pageable, name);
        }else{
            pageResult = sourceRepository.findAll(pageable);
        }

        Map<String, Object> customPage = new HashMap<>();
        customPage.put("status", HttpStatus.OK);
        customPage.put("content", pageResult.getContent().stream()
                .map(article -> sourceConverter.toDto(article))
                .collect(Collectors.toList()));
        customPage.put("page", pageResult.getNumber());
        customPage.put("size", pageResult.getSize());
        customPage.put("totalElements", pageResult.getTotalElements());
        return customPage;
    }

    private void setSourceCode(SourceDTO sourceDTO){
        sourceDTO.setCode(sourceDTO.getName().toLowerCase().replace(" ", "-"));
    }
}
