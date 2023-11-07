package com.dcfB.service;

import com.dcfB.model.OS_Report;
import com.dcfB.repository.OS_reportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OS_reportService {

    @Autowired
    OS_reportRepository repo;


    public List<OS_Report> listAll() {
        return repo.findAll();
    }

    public void save(OS_Report report) {
        repo.save(report);
    }

    public Optional<OS_Report> get(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public void fillReport(String dateReport) {
        repo.deleteAll();

        List<Object[]> list = repo.listReport(dateReport);
        for (Object[] record : list) {
            OS_Report rp = new OS_Report();
            rp.setRecordName((String) record[0]);
            rp.setCode((String) record[1]);
            rp.setQuantity(((Long) record[2]).intValue());
            rp.setQuantityDemand(0);
            rp.setNote("");
            repo.save(rp);
        }
    }



}
