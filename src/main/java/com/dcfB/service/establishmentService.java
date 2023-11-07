package com.dcfB.service;

import com.dcfB.model.Establishment;
import com.dcfB.repository.establishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class establishmentService {
    
    @Autowired
    private establishmentRepository repo;


    public List<Establishment> listAll() {
        List<Establishment> list1 = null;
        try {
             List<Establishment> list= Collections.singletonList(repo.findAll().stream().findFirst().orElseThrow(() -> new Exception("Establishment not found - ")));
             list1=list;
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return list1;
    }

    public void save(Establishment establishment) {
        repo.save(establishment);
    }

    public Optional<Establishment> get(Integer id) {
        return repo.findById(id);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Establishment getLast(){
        return repo.findTopByOrderByIdDesc();
    }
}
