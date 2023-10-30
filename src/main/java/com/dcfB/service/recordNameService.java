package com.dcfB.service;

import com.dcfB.model.Records;
import com.dcfB.repository.recordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcfB.model.RecordName;
import com.dcfB.repository.recordNameRepository;

import java.util.List;
import java.util.Optional;

@Service
public class recordNameService {
	@Autowired
	private recordNameRepository repo;
	@Autowired
	private recordsRepository repoRec;
	
	public List<RecordName> listAll(){
		return repo.findAll();
	}

	public RecordName getOneRn(Long id){
		return repo.getOne(id);
	}

	public void save(RecordName recName) {
		repo.save(recName);
	}
	
	public Optional<RecordName> get(Long id) {
		return repo.findById(id);
	}
	
	public void delete(Long id) {
		for (Records r : repoRec.findRecordNamesRecords(id)){
			repoRec.delete(r);
		}
		repo.deleteById(id);
	}

}
