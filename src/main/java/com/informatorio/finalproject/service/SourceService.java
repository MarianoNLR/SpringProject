package com.informatorio.finalproject.service;

import com.informatorio.finalproject.converter.SourceConverter;
import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.SourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SourceService {

    private SourceRepository sourceRepository;

    private final SourceConverter sourceConverter;



    public SourceService(SourceRepository sourceRepository, SourceConverter sourceConverter) {
        this.sourceRepository = sourceRepository;
        this.sourceConverter = sourceConverter;
    }



    public boolean saveSource(SourceDTO sourceDTO){
        Source source = sourceConverter.toEntity(sourceDTO);
        sourceRepository.save(source);
        return true;

    }

    public Source updateSource(SourceDTO sourceDTO){
        Source source = sourceConverter.toEntity(sourceDTO);
        sourceRepository.save(source);
        return source;
    }

    /*public List<SourceDTO> getAllSources(){
        return sourceRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }*/
}
