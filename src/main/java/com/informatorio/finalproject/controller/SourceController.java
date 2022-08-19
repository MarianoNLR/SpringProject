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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RequestMapping("/api/v1")
@RestController
public class SourceController {

    SourceService sourceService;
    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @RequestMapping(value = "/sources", method = RequestMethod.GET)
    public ResponseEntity<?> getSources(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "") String name){
        name = name.replace("-", " ");
        return new ResponseEntity<>(sourceService.getAllSources(page, name), HttpStatus.OK);

    }

    @PostMapping("/sources/add")
    public ResponseEntity<?> createSource(@RequestBody SourceDTO sourceDTO){
        return new ResponseEntity<>(sourceService.saveSource(sourceDTO), HttpStatus.OK);
    }

    @PutMapping("/sources/{id}")
    public ResponseEntity<?> updateSource(@PathVariable Long id ,@Valid @RequestBody SourceDTO sourceDTO) {

        SourceDTO sourceDTOAux = sourceService.updateSource(sourceDTO, id);

        if (sourceDTOAux != null){
            return new ResponseEntity<>(sourceDTOAux, HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("sources/{id}")
    public boolean deleteSource(@PathVariable Long id){
        return sourceService.deleteSource(id);
    }


}
