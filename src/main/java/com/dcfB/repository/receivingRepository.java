package com.dcfB.repository;

import com.dcfB.model.Receiving;
import com.dcfB.model.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface receivingRepository extends JpaRepository<Receiving,Integer> {
    @Query(value = "select r from Records r where r.receiving.id=?1")
    List<Records> listReceivingRecords(int id);


    Receiving findFirstByOrderByIdDesc();
}
