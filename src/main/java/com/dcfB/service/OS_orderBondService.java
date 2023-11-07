package com.dcfB.service;

import com.dcfB.model.*;
import com.dcfB.repository.OS_orderBondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class OS_orderBondService {
    public static String uploadDirectory=System.getProperty("user.dir")+"/uploads";

    @Autowired
    OS_orderBondRepository repo;

    public List<OS_OrderBond> listAll(){
        return repo.findAll();
    }

    public List<OS_ReceivingEnter> listAllReceivingEnter(int id){
        return repo.listOrderReceivingEnter(id);
    }

    public List<OS_Records> listAllRecordsOrederBond(int id){
        return repo.recordsDependOnOrderBond(id);
    }

    public List<OS_Records> listAllRecordsReceivingEnter(int id){
        return repo.recordsDependOnReceivingEnter(id);
    }

    public void save(OS_OrderBond orderBond) {
        repo.save(orderBond);
    }

    public Optional<OS_OrderBond> get(Integer id) {
        return repo.findById(id);
    }

    public OS_OrderBond getById(Integer id) {
        return repo.getOne(id);
    }

    public OS_OrderBond getLast(){

        return repo.findFirstByOrderByIdDesc();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public void recordsListOfOrderBond(Integer id) {
        repo.listOrderReceivingEnter(id);
    }

    public Integer stateOrderBond(Integer id) {
        return repo.orderBondState(id);
    }



    public boolean deleteFile(Integer id,String file){
        boolean status=false;
        try {
            if (id!=0 && file!=null){
                String path=uploadDirectory+"/"+file;
                File fileToDelete=new File(path);
                status=fileToDelete.delete();
                return status;
            }
        }catch (Exception e){
            e.printStackTrace();
            return status;
        }
        return status;
    }



}
