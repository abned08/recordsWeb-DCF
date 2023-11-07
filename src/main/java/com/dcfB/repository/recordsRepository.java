package com.dcfB.repository;

import com.dcfB.model.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface recordsRepository extends JpaRepository<Records,Integer> {
    Records findFirstByRecordNameRecname (String recname);

    @Override
    @Query("select r from Records r where r.orderBondQuantity is null ")
    List<Records>findAll();

    @Query("select r from Records r where r.recordName.Id = ?1")
    List<Records>findRecordNamesRecords(Long Id);

    @Query("select ((coalesce(sum (r.recordin),0)) -(coalesce(sum (r.recordout),0)) ) from Records r where r.recordName.Id=?1")
    Integer sum(Long rn);

    @Query(value = "SELECT distinct on( r.record_name_id) r.* FROM records r ",nativeQuery = true)
    List<Records> listDistinct();

    @Query("select r from Records r where r.recordName.code=?1")
    List<Records> listRecordsSame(String code);

    @Query("select (coalesce(sum (r.orderBondQuantity),0)) -(coalesce(sum (r.recordin),0))  from Records r where r.recordName.Id=?1 and r.orderBond.id=?2")
    Integer sumOrderBond(Long rn,Integer orderBond);








}
