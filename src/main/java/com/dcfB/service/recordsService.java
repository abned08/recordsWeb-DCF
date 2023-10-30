package com.dcfB.service;

import com.dcfB.model.RecordName;
import com.dcfB.model.Records;
import com.dcfB.model.Services;
import com.dcfB.repository.recordNameRepository;
import com.dcfB.repository.recordsRepository;
import com.dcfB.repository.serviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class recordsService {

    @Autowired
    private recordsRepository repo;

    @Autowired
    private recordNameRepository rnrepo;

    @Autowired
    private serviceRepository serrepo;

    public List<Records> listAll(){
        return repo.findAll();
    }

    public List<Records> listDistinct(){
        return repo.listDistinct();
    }

    public List<Records> listSame(String code){
        return repo.listRecordsSame(code);
    }


    public void save(Records records) {
        repo.save(records);
    }

    public void saveAll(List<Records> records) {
        repo.saveAll(records);
    }

    public Optional<Records> get(Integer id) {
        return repo.findById(id);
    }

    public Records findRecord(Integer id){
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
    public Records findByRecordName(String recname){
        return repo.findFirstByRecordNameRecname(recname);
    }

    public List<Records> initRecordsList(){
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        List<RecordName>rn=rnrepo.findAll();
        Services services=serrepo.findByServicename("المخزون الأولي");
//        repo.deleteAll();
        List<Records> list=new ArrayList<>();
        for (RecordName recordN:rn){
            recordN.setSafetyStock(0);
        }
        for (int i=0;i<rn.size();i++){
            Records r=new Records();
            r.setRecorddate(date);
            r.setRecordName(rn.get(i));
            r.setServices(services);
            list.add(r);
        }
        return list;
    }
}
