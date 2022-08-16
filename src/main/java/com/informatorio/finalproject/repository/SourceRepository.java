package com.informatorio.finalproject.repository;


import com.informatorio.finalproject.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {


}
