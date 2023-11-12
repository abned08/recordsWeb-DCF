package com.dcfB.service;

import com.dcfB.model.Report;
import com.dcfB.repository.reportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class reportService {

    @Autowired
    reportRepository repo;


    public List<Report> listAll() {
        return repo.findAll();
    }

    public void save(Report report) {
        repo.save(report);
    }

    public Optional<Report> get(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public void fillReport(String dateReport,String dateReportEnd) {
        repo.deleteAll();

        List<Object[]> list = repo.listReport(dateReport, dateReportEnd);
        for (Object[] record : list) {
            Report rp = new Report();
            rp.setRecordName((String) record[0]);
            rp.setCode((String) record[1]);
            rp.setQuantity(((Long) record[2]).intValue());
            rp.setQuantityDemand(0);
            rp.setNote("");
            repo.save(rp);
        }
    }



}
