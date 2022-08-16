package com.informatorio.finalproject.controller;


import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
