package com.dcfB.repository;

import com.dcfB.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface receivingEnterRepository extends JpaRepository<ReceivingEnter,Integer> {

    @Query(value = "select r from Records r where r.receiving_enter.id=?1")
    List<Records> listReceivingEnterRecords(int id);

    ReceivingEnter findFirstByOrderByIdDesc();

    ReceivingEnter findFirstByOrderBond(OrderBond orderBond);

    @Query(value = "select rn from RecordName rn left join Records r on r.recordName.Id=rn.Id where r.orderBond.id=?1 group by rn having (coalesce(sum (r.orderBondQuantity),0)) -(coalesce(sum (r.recordin),0)) >0")
    List<RecordName> orderBondRecordNames(Integer id);

}
