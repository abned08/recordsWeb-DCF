package com.dcfB.service;

import com.dcfB.model.*;
import com.dcfB.repository.OS_receivingEnterRepository;
import com.dcfB.repository.receivingEnterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class OS_receivingEnterService {

    public static String uploadDirectory=System.getProperty("user.dir")+"/uploads";

    @Autowired
    OS_receivingEnterRepository repo;

    public List<OS_ReceivingEnter> listAll(){
        return repo.findAll();
    }

    public List<OS_Records> listAllRecords(int id){
        return repo.listReceivingEnterRecords(id);
    }

    public void save(OS_ReceivingEnter receivingEnter) {
        repo.save(receivingEnter);
    }

    public Optional<OS_ReceivingEnter> get(Integer id) {
        return repo.findById(id);
    }

    public OS_ReceivingEnter getById(Integer id) {
        return repo.getOne(id);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public OS_ReceivingEnter getLast(){

        return repo.findFirstByOrderByIdDesc();
    }

    public OS_ReceivingEnter getbyOrderBond(OS_OrderBond orderBond){

        return repo.findFirstByOrderBond(orderBond);
    }

    public List<OS_RecordName> recordNames(Integer id){

        return repo.orderBondRecordNames(id);
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
