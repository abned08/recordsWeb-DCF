package com.dcfB.repository;

import com.dcfB.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface reportRepository extends JpaRepository<Report, Long> {
    @Query("select rn.recname, rn.code, ((coalesce(sum (r.recordin),0)) -(coalesce(sum (r.recordout),0)) ) from RecordName rn left join Records r on rn.Id=r.recordName.Id and r.recorddate between ?1 and ?2  group by rn.code,rn.recname order by rn.code")
    List<Object[]> listReport(String dateReport, String dateReportEnd);

    @Override
    @Query("select r from Report r order by r.code")
    List<Report> findAll();

}
