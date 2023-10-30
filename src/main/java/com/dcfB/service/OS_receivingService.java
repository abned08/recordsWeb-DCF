package com.dcfB.service;

import com.dcfB.model.OS_Receiving;
import com.dcfB.model.OS_Records;
import com.dcfB.model.Receiving;
import com.dcfB.model.Records;
import com.dcfB.repository.OS_receivingRepository;
import com.dcfB.repository.receivingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OS_receivingService {
    
    @Autowired
    OS_receivingRepository repo;

    public List<OS_Receiving> listAll(){
        return repo.findAll();
    }

    public List<OS_Records> listAllRecords(int id){
        return repo.listReceivingRecords(id);
    }

    public void save(OS_Receiving receiving) {
        repo.save(receiving);
    }

    public Optional<OS_Receiving> get(Integer id) {
        return repo.findById(id);
    }

    public OS_Receiving getById(Integer id) {
        return repo.getOne(id);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public OS_Receiving getLast(){

        return repo.findFirstByOrderByIdDesc();
    }
}
