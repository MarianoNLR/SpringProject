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

    private SourceDTO toDTO(Source source){
        SourceDTO sourceDTO = new SourceDTO();

        sourceDTO.setId(source.getId());
        sourceDTO.setCode(source.getCode());
        sourceDTO.setName(source.getName());
        sourceDTO.setCreatedAt(source.getCreatedAt());
        return sourceDTO;
    }

    public boolean saveSource(SourceDTO sourceDTO){
        Source source = sourceConverter.convert(sourceDTO);
        sourceRepository.save(source);
        return true;

    }

    public List<SourceDTO> getAllSources(){
        return sourceRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
