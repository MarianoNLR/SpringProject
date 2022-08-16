package com.informatorio.finalproject.converter;

import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.SourceRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SourceConverter{
    private final SourceRepository sourceRepository;


    public SourceConverter(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public Source toEntity(SourceDTO sourceDTO){
        Source source = new Source();

        source.setId(sourceDTO.getId());
        source.setCode(sourceDTO.getCode());
        source.setName(sourceDTO.getName());
        source.setCreatedAt(sourceDTO.getCreatedAt());

        return source;
    }

    public SourceDTO toDto(Source source){
        SourceDTO sourceDTO = new SourceDTO();

        sourceDTO.setId(source.getId());
        sourceDTO.setCode(source.getCode());
        sourceDTO.setName(source.getName());
        sourceDTO.setCreatedAt(source.getCreatedAt());
        return sourceDTO;
    }

}
