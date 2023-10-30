package com.dcfB.repository;

import com.dcfB.model.OrderBond;
import com.dcfB.model.ReceivingEnter;
import com.dcfB.model.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface orderBondRepository extends JpaRepository<OrderBond,Integer> {
    @Query(value = "select r from ReceivingEnter r where r.orderBond.id=?1")
    List<ReceivingEnter> listOrderReceivingEnter(int id);

    OrderBond findFirstByOrderByIdDesc();

    @Query("select r from Records r where r.receiving_enter.orderBond.id=?1")
    List<Records> recordsDependOnReceivingEnter(int id);


    @Query("select r from Records r where r.orderBond.id=?1 and r.receiving_enter.id is null")
    List<Records> recordsDependOnOrderBond(int id);

    @Query("select (coalesce(sum (r.recordin),0)-coalesce(sum (r.orderBondQuantity),0)) from Records r where r.orderBond.id=?1")
    Integer orderBondState(Integer id);


}
