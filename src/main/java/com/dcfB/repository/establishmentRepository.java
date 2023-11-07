package com.dcfB.repository;

import com.dcfB.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface establishmentRepository extends JpaRepository<Establishment,Integer> {
     Establishment findTopByOrderByIdDesc();
}
