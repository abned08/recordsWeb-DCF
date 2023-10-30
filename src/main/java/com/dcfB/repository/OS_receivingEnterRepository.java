package com.dcfB.repository;

import com.dcfB.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OS_receivingEnterRepository extends JpaRepository<OS_ReceivingEnter,Integer> {

    @Query(value = "select r from OS_Records r where r.receiving_enter.id=?1")
    List<OS_Records> listReceivingEnterRecords(int id);

    //todo change to os
    OS_ReceivingEnter findFirstByOrderByIdDesc();
    //todo change to os
    OS_ReceivingEnter findFirstByOrderBond(OS_OrderBond orderBond);

    @Query(value = "select rn from OS_RecordName rn left join OS_Records r on r.recordName.Id=rn.Id where r.orderBond.id=?1 group by rn having (coalesce(sum (r.orderBondQuantity),0)) -(coalesce(sum (r.recordin),0)) >0")
    List<OS_RecordName> orderBondRecordNames(Integer id);

}
