package com.dcfB.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcfB.model.RecordName;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface recordNameRepository extends JpaRepository<RecordName, Long> {

}
