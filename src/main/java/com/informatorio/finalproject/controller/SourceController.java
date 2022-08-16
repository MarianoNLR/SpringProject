package com.informatorio.finalproject.controller;


import com.informatorio.finalproject.converter.SourceConverter;
import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SourceController {

    SourceRepository sourceRepository;

    SourceConverter sourceConverter;
    SourceService sourceService;
    @Autowired
    public SourceController(SourceRepository sourceRepository, SourceConverter sourceConverter, SourceService sourceService) {
        this.sourceRepository = sourceRepository;
        this.sourceConverter = sourceConverter;
        this.sourceService = sourceService;
    }

    @RequestMapping(value = "/sources", method = RequestMethod.GET)
    public ResponseEntity<?> getSources(@RequestParam int page){
        PageRequest pageable = PageRequest.of(page, 3);
        Page<Source> pageResult = sourceRepository.findAll(pageable);

        Map<String, Object> customPage = new HashMap<>();
        customPage.put("status", HttpStatus.OK);
        customPage.put("content", pageResult.getContent().stream()
                .map(article -> sourceConverter.toDto(article))
                .collect(Collectors.toList()));
        customPage.put("page", pageResult.getNumber());
        customPage.put("size", pageResult.getSize());
        customPage.put("totalElements", pageResult.getTotalElements());

        return new ResponseEntity<>(customPage, HttpStatus.OK);

    }

    @PostMapping("/sources")
    public ResponseEntity<?> createSource(@RequestBody SourceDTO sourceDTO){
        return new ResponseEntity<>(sourceService.saveSource(sourceDTO), HttpStatus.OK);
    }

    @PutMapping("/sources")
    public Source updateSource(@RequestBody SourceDTO sourceDTO){
        return sourceService.updateSource(sourceDTO);
    }

}
