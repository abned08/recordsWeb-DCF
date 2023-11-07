package com.dcfB.repository;

import com.dcfB.model.OS_Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OS_reportRepository extends JpaRepository<OS_Report, Long> {
    @Query("select rn.recname, rn.code, ((coalesce(sum (r.recordin),0)) -(coalesce(sum (r.recordout),0)) ) from OS_RecordName rn left join OS_Records r on rn.Id=r.recordName.Id and r.recorddate<=?1  group by rn.code,rn.recname order by rn.code")
    List<Object[]> listReport(String dateReport);

    @Override
    @Query("select r from OS_Report r order by r.code")
    List<OS_Report> findAll();
}
