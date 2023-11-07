package com.dcfB.controller;

import com.dcfB.exception.MyFileNotFoundException;
import com.dcfB.model.*;
import com.dcfB.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class OS_receivingEnterController {


    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Autowired
    OS_receivingEnterService service;
    @Autowired
    OS_orderBondService ordService;
    @Autowired
    private establishmentService estService;
    @Autowired
    private serviceService serservice;
    @Autowired
    private OS_recordsService recService;
    @Autowired
    private OS_recordNameService recNameService;


    int ordId;

    @RequestMapping("/OS_receivingEnterList/id={id}")
    public String OS_viewReceivingEnter(Model model, @ModelAttribute("receivingEnter") OS_ReceivingEnter receivingEnter, @PathVariable("id") int id) {
        this.ordId = id;
        List<OS_ReceivingEnter> list = ordService.listAllReceivingEnter(id);
        model.addAttribute("receiveEnterList", list);
        model.addAttribute("serReceivingNameList", serservice.listAll());
        model.addAttribute("orderBond", ordService.getById(id));
        model.addAttribute("estList", estService.listAll());
        return "OS_receivingsEnter_List";
    }

    @PostMapping("/OS_saveReceivingEnter")
    public String OS_saveReceivingEnter(OS_ReceivingEnter receivingEnter, @RequestParam("file") MultipartFile file,
                                     @RequestParam(value = "fName", required = false) String fName,
                                     @RequestParam(value = "fPath", required = false) String fPath,
                                     @RequestParam(value = "fType", required = false) String fType,
                                     @RequestParam(value = "fSize", required = false) String fSize) throws IOException {


        if (file.isEmpty()) {
            if (fName != "") {
                receivingEnter.setFileName(fName);
                receivingEnter.setFilePath(fPath);
                receivingEnter.setFileType(fType);
                receivingEnter.setFileSize(fSize);
            } else {
                receivingEnter.setFileName("");
                receivingEnter.setFilePath("");
                receivingEnter.setFileType("");
                receivingEnter.setFileSize("");
            }
        } else {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
            Date date = new Date();
            String s = formatter.format(date);
            // String fileName = file.getOriginalFilename();
            String fNameWithDate = fName + s + ".jpg";
            String filePath = Paths.get(uploadDirectory, fNameWithDate).toString();
            String fileType = file.getContentType();
            String size = String.valueOf(file.getSize());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(file.getBytes());
            stream.close();
            receivingEnter.setFileName(fNameWithDate);
            receivingEnter.setFilePath(filePath);
            receivingEnter.setFileType(fileType);
            receivingEnter.setFileSize(size);
        }
        OS_OrderBond orderBond = ordService.getById(ordId);
        receivingEnter.setOrderBond(orderBond);
        receivingEnter.setServicesReceivingEnter(orderBond.getServicesOrderBond());

        service.save(receivingEnter);


        return "redirect:/OS_receivingEnterList/id="+ordId;

    }

    @GetMapping("/OS_removeFile/{id}/{deleteFileName}")
    public String OS_removeFile(@PathVariable("id") Integer id, @PathVariable("deleteFileName") String deleteFileName) {
        String path = null;
        File file = null;
        try {
            path = uploadDirectory + "/" + deleteFileName;
            file = new File(path);
            if (file.exists()) {
                service.deleteFile(id, deleteFileName);
            }
            return "redirect:/OS_receivingEnterList/id="+ordId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyFileNotFoundException("الصورة غير موجودة: " + deleteFileName);
        }
    }


    @GetMapping("/OS_deleteReceivingEnter")
    public String OS_deleteReceivingEnter(Integer id) {
        service.delete(id);
        return "redirect:/OS_receivingEnterList/id="+ordId;
    }

    @GetMapping("/OS_findReceivingEnter")
    @ResponseBody
    public Optional<OS_ReceivingEnter> OS_findReceivingEnter(Integer id) {
        return service.get(id);
    }


    ///////_______________________*******______________________________receivingEnterToAddController

    int idd;

    @RequestMapping("/OS_findReceivingEnterToAddRecords/id={id}")
    public String OS_findReceivingEnterToAddRecords(@PathVariable("id") Integer id, Model model, @ModelAttribute OS_Records records) {
        this.idd = id;
        OS_ReceivingEnter re=service.getById(id);
        model.addAttribute("records", new OS_Records());
        model.addAttribute("receivingEnter", re);
        model.addAttribute("recNameList", service.recordNames(re.getOrderBond().getId()));
        model.addAttribute("listRecords", service.listAllRecords(id));
        model.addAttribute("estList", estService.listAll());
        return "OS_receivingEnterToAdd_List";
    }

    @PostMapping("/OS_saveRecordToReceivingEnter")
    public String OS_saveRecordToReceivingEnter(@ModelAttribute("records") OS_Records record, @RequestParam("count") Integer count) {
        OS_ReceivingEnter receivingEnter = service.getById(idd);
        OS_OrderBond orderBond=receivingEnter.getOrderBond();
        record.setReceiving_enter(receivingEnter);
        record.setOrderBond(receivingEnter.getOrderBond());
        record.setServices(receivingEnter.getServicesReceivingEnter());
        record.setRecorddate(receivingEnter.getReceive_enter_date());
        OS_RecordName rn = recNameService.getOneRn(record.getRecordName().getId());
        rn.setCount(count);
        recService.save(record);
        recNameService.save(rn);
        orderBond.setState(OS_stateOfOrderBond(orderBond));
        ordService.save(orderBond);

        return "redirect:/OS_findReceivingEnterToAddRecords/id=" + idd;

    }

    private String OS_stateOfOrderBond(OS_OrderBond orderBond) {
        if (ordService.listAllRecordsReceivingEnter(orderBond.getId()).size()==0){
            return "لم يتم التسليم بعد";
        }else {
            if (ordService.stateOrderBond(orderBond.getId())==0){
                return "تم تسليم كل المطبوعات";
            }else {
                return "تسليم جزئي";
            }
        }
    }

    @GetMapping("/OS_findReceivingEnterRecord")
    @ResponseBody
    public OS_Records OS_findReceivingEnterRecord(Integer id) {
        OS_Records record = recService.findRecord(id);
        Integer count = recService.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }


    @GetMapping("/OS_deleteReceivingEnterRecord")
    public String OS_deleteReceivingEnterRecord(Integer id) {
        OS_OrderBond o=recService.get(id).get().getReceiving_enter().getOrderBond();
        recService.delete(id);
        o.setState(OS_stateOfOrderBond(o));
        ordService.save(o);

        return "redirect:/OS_findReceivingEnterToAddRecords/id=" + idd;
    }

    @GetMapping("/OS_findLastAddOneEnter")
    @ResponseBody
    public Integer OS_findLastAddOneEnter() {
        OS_ReceivingEnter r = service.getLast();

        if (r != null)
            return r.getReceive_enter_num() + 1;
        else
            return 1;

    }


}
