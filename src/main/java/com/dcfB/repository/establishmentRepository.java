package com.dcfB.repository;

import com.dcfB.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface establishmentRepository extends JpaRepository<Establishment,Integer> {
     Establishment findTopByOrderByIdDesc();
}
