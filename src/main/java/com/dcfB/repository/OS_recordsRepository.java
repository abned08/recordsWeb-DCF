package com.dcfB.repository;

import com.dcfB.model.OS_Records;
import com.dcfB.model.Records;
import com.sun.xml.bind.v2.TODO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OS_recordsRepository extends JpaRepository<OS_Records,Integer> {
    OS_Records findFirstByRecordNameRecname(String recname);

    @Override
    @Query("select r from OS_Records r where r.orderBondQuantity is null ")
    List<OS_Records>findAll();

    @Query("select r from OS_Records r where r.recordName.Id = ?1")
    List<OS_Records>findRecordNamesRecords(Long Id);

    @Query("select ((coalesce(sum (r.recordin),0)) -(coalesce(sum (r.recordout),0)) ) from OS_Records r where r.recordName.Id=?1")
    Integer sum(Long rn);

    //TODO change records here
    @Query(value = "SELECT distinct on( r.record_name_id) r.* FROM os_records r ",nativeQuery = true)
    List<OS_Records> listDistinct();

    @Query("select r from OS_Records r where r.recordName.code=?1")
    List<OS_Records> listRecordsSame(String code);

    @Query("select (coalesce(sum (r.orderBondQuantity),0)) -(coalesce(sum (r.recordin),0))  from OS_Records r where r.recordName.Id=?1 and r.orderBond.id=?2")
    Integer sumOrderBond(Long rn, Integer orderBond);








}
