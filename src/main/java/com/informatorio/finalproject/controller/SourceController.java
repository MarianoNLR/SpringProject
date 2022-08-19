package com.informatorio.finalproject.controller;


import com.informatorio.finalproject.converter.SourceConverter;
import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
public class SourceController {

    SourceService sourceService;

    SourceConverter sourceConverter;
    @Autowired
    public SourceController(SourceService sourceService, SourceConverter sourceConverter) {
        this.sourceService = sourceService;
        this.sourceConverter = sourceConverter;
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

        Source source = sourceService.updateSource(sourceDTO, id);

        if (source != null){
            return new ResponseEntity<>(sourceConverter.toDto(source), HttpStatus.ACCEPTED);
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
