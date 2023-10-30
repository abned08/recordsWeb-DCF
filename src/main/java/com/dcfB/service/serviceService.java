package com.dcfB.service;


import com.dcfB.model.Services;
import com.dcfB.repository.serviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class serviceService {

    @Autowired
    private serviceRepository repo;

    public List<Services> listAll(){
        return repo.findAll();
    }

    public List<Services> listAll1(){
        return repo.findAll1();
    }

    public void save(Services services) {
        repo.save(services);
    }

    public Optional<Services> get(Integer id) {
        return repo.findById(id);
    }
    public Services getBySName(String sname) {
        return repo.findByServicename(sname);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
