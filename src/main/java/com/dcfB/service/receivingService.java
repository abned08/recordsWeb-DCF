package com.dcfB.service;

import com.dcfB.model.Receiving;
import com.dcfB.model.Records;
import com.dcfB.repository.receivingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class receivingService {
    
    @Autowired
    receivingRepository repo;

    public List<Receiving> listAll(){
        return repo.findAll();
    }

    public List<Records> listAllRecords(int id){
        return repo.listReceivingRecords(id);
    }

    public void save(Receiving receiving) {
        repo.save(receiving);
    }

    public Optional<Receiving> get(Integer id) {
        return repo.findById(id);
    }

    public Receiving getById(Integer id) {
        return repo.getOne(id);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Receiving getLast(){

        return repo.findFirstByOrderByIdDesc();
    }
}
