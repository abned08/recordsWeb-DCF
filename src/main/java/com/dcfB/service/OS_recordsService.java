package com.dcfB.service;

import com.dcfB.model.*;
import com.dcfB.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OS_recordsService {

    @Autowired
    private OS_recordsRepository repo;

    @Autowired
    private OS_recordNameRepository rnrepo;

    @Autowired
    private serviceRepository serrepo;

    public List<OS_Records> listAll(){
        return repo.findAll();
    }

    public List<OS_Records> listDistinct(){
        return repo.listDistinct();
    }

    public List<OS_Records> listSame(String code){
        return repo.listRecordsSame(code);
    }


    public void save(OS_Records records) {
        repo.save(records);
    }

    public void saveAll(List<OS_Records> records) {
        repo.saveAll(records);
    }

    public Optional<OS_Records> get(Integer id) {
        return repo.findById(id);
    }

    public OS_Records findRecord(Integer id){
        return repo.getOne(id);
    }


    public void delete(Integer id) {
        repo.deleteById(id);
    }
    public void deleteAll() {
        repo.deleteAll();
    }

    public Integer sum(Long s){
        return repo.sum(s);
    }

    public Integer sumOrderBond(Long s,Integer o){
        return repo.sumOrderBond(s,o);
    }
    public OS_Records findByRecordName(String recname){
        return repo.findFirstByRecordNameRecname(recname);
    }

    public List<OS_Records> initRecordsList(){
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        List<OS_RecordName>rn=rnrepo.findAll();
        Services services=serrepo.findByServicename("المخزون الأولي");
//        repo.deleteAll();
        List<OS_Records> list=new ArrayList<>();
        for (OS_RecordName recordN:rn){
            recordN.setSafetyStock(0);
        }
        for (int i=0;i<rn.size();i++){
            OS_Records r=new OS_Records();
            r.setRecorddate(date);
            r.setRecordName(rn.get(i));
            r.setServices(services);
            list.add(r);
        }
        return list;
    }
}
