package com.dcfB.repository;

import com.dcfB.model.OS_Receiving;
import com.dcfB.model.OS_Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OS_receivingRepository extends JpaRepository<OS_Receiving,Integer> {
    @Query(value = "select r from OS_Records r where r.receiving.id=?1")
    List<OS_Records> listReceivingRecords(int id);

    //todo change to os
    OS_Receiving findFirstByOrderByIdDesc();
}
