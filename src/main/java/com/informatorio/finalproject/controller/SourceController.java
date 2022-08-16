package com.informatorio.finalproject.controller;


import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SourceController {
    @Autowired
    SourceRepository sourceRepository;
    @Autowired
    SourceService sourceService;

    @RequestMapping(value = "/sources", method = RequestMethod.GET)
    public List<Source> findSource(){
        return sourceRepository.findAll();
    }

    @PostMapping("/sources")
    public ResponseEntity<?> createSource(@RequestBody SourceDTO sourceDTO){
        return new ResponseEntity<>(sourceService.saveSource(sourceDTO), HttpStatus.OK);
    }

}
