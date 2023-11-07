package com.dcfB.service;

import com.dcfB.model.OrderBond;
import com.dcfB.model.ReceivingEnter;
import com.dcfB.model.RecordName;
import com.dcfB.model.Records;
import com.dcfB.repository.receivingEnterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class receivingEnterService {

    public static String uploadDirectory=System.getProperty("user.dir")+"/uploads";

    @Autowired
    receivingEnterRepository repo;

    public List<ReceivingEnter> listAll(){
        return repo.findAll();
    }

    public List<Records> listAllRecords(int id){
        return repo.listReceivingEnterRecords(id);
    }

    public void save(ReceivingEnter receivingEnter) {
        repo.save(receivingEnter);
    }

    public Optional<ReceivingEnter> get(Integer id) {
        return repo.findById(id);
    }

    public ReceivingEnter getById(Integer id) {
        return repo.getOne(id);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public ReceivingEnter getLast(){

        return repo.findFirstByOrderByIdDesc();
    }

    public ReceivingEnter getbyOrderBond(OrderBond orderBond){

        return repo.findFirstByOrderBond(orderBond);
    }

    public List<RecordName> recordNames(Integer id){

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
