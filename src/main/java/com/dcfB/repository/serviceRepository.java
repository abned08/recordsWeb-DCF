package com.dcfB.repository;

import com.dcfB.model.Records;
import com.dcfB.model.Services;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface serviceRepository extends JpaRepository<Services,Integer> {

    Services findByServicename(String name);

    @Override
    @Query("select s from Services s where not s.servicename='المخزون الأولي' ")
    List<Services>findAll();

    @Query("select s from Services s ")
    List<Services>findAll1();

}
