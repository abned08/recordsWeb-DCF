package com.dcfB.service;

import com.dcfB.model.OS_RecordName;
import com.dcfB.model.OS_Records;
import com.dcfB.repository.OS_recordNameRepository;
import com.dcfB.repository.OS_recordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OS_recordNameService {
	@Autowired
	private OS_recordNameRepository repo;
	@Autowired
	private OS_recordsRepository repoRec;
	
	public List<OS_RecordName> listAll(){
		return repo.findAll();
	}

	public OS_RecordName getOneRn(Long id){
		return repo.getOne(id);
	}

	public void save(OS_RecordName recName) {
		repo.save(recName);
	}
	
	public Optional<OS_RecordName> get(Long id) {
		return repo.findById(id);
	}
	
	public void delete(Long id) {
		for (OS_Records r : repoRec.findRecordNamesRecords(id)){
			repoRec.delete(r);
		}
		repo.deleteById(id);
	}

}
