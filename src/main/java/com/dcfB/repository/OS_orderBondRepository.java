package com.dcfB.repository;

import com.dcfB.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OS_orderBondRepository extends JpaRepository<OS_OrderBond,Integer> {
    @Query(value = "select r from OS_ReceivingEnter r where r.orderBond.id=?1")
    List<OS_ReceivingEnter> listOrderReceivingEnter(int id);

    //todo change to os
    OS_OrderBond findFirstByOrderByIdDesc();

    @Query("select r from OS_Records r where r.receiving_enter.orderBond.id=?1")
    List<OS_Records> recordsDependOnReceivingEnter(int id);


    @Query("select r from OS_Records r where r.orderBond.id=?1 and r.receiving_enter.id is null")
    List<OS_Records> recordsDependOnOrderBond(int id);

    @Query("select (coalesce(sum (r.recordin),0)-coalesce(sum (r.orderBondQuantity),0)) from OS_Records r where r.orderBond.id=?1")
    Integer orderBondState(Integer id);


}
