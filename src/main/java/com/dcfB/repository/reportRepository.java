package com.dcfB.repository;

import com.dcfB.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface reportRepository extends JpaRepository<Report, Long> {
    @Query("select rn.recname, rn.code, sum (case when r.recorddate between ?1 and ?2 then r.recordout else 0 end), sum (case when r.recorddate <= ?2 then r.recordin else 0 end) - sum (case when r.recorddate <= '2023/11/13' then r.recordout else 0 end) from records_db.record_name rn left join  records_db.Records r on rn.id = r.record_name_id group by rn.code,rn.recname  order by rn.code")
    List<Object[]> listReport(String dateReport, String dateReportEnd);

    @Override
    @Query("select r from Report r order by r.code")
    List<Report> findAll();

}
