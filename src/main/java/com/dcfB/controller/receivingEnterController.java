package com.dcfB.controller;

import com.dcfB.exception.MyFileNotFoundException;
import com.dcfB.model.*;
import com.dcfB.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class receivingEnterController {


    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Autowired
    receivingEnterService service;
    @Autowired
    orderBondService ordService;
    @Autowired
    private establishmentService estService;
    @Autowired
    private serviceService serservice;
    @Autowired
    private recordsService recService;
    @Autowired
    private recordNameService recNameService;


    int ordId;

    @RequestMapping("/receivingEnterList/id={id}")
    public String viewReceivingEnter(Model model, @ModelAttribute("receivingEnter") ReceivingEnter receivingEnter, @PathVariable("id") int id) {
        this.ordId = id;
        List<ReceivingEnter> list = ordService.listAllReceivingEnter(id);
        model.addAttribute("receiveEnterList", list);
        model.addAttribute("serReceivingNameList", serservice.listAll());
        model.addAttribute("orderBond", ordService.getById(id));
        model.addAttribute("estList", estService.listAll());
        model.addAttribute("title","بيانات الاستلام");
        return "receivingsEnter_List";
    }

    @PostMapping("/saveReceivingEnter")
    public String saveReceivingEnter(ReceivingEnter receivingEnter, @RequestParam("file") MultipartFile file,
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
        OrderBond orderBond = ordService.getById(ordId);
        receivingEnter.setOrderBond(orderBond);
        receivingEnter.setServicesReceivingEnter(orderBond.getServicesOrderBond());

        service.save(receivingEnter);


        return "redirect:/receivingEnterList/id="+ordId;

    }

    @GetMapping("/removeFile/{id}/{deleteFileName}")
    public String removeFile(@PathVariable("id") Integer id, @PathVariable("deleteFileName") String deleteFileName) {
        String path = null;
        File file = null;
        try {
            path = uploadDirectory + "/" + deleteFileName;
            file = new File(path);
            if (file.exists()) {
                service.deleteFile(id, deleteFileName);
            }
            return "redirect:/receivingEnterList/id="+ordId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyFileNotFoundException("الصورة غير موجودة: " + deleteFileName);
        }
    }


    @GetMapping("/deleteReceivingEnter")
    public String deleteReceivingEnter(Integer id) {
        service.delete(id);
        return "redirect:/receivingEnterList/id="+ordId;
    }

    @GetMapping("/findReceivingEnter")
    @ResponseBody
    public Optional<ReceivingEnter> findReceivingEnter(Integer id) {
        return service.get(id);
    }


    ///////_______________________*******______________________________receivingEnterToAddController

    int idd;

    @RequestMapping("/findReceivingEnterToAddRecords/id={id}")
    public String findReceivingEnterToAddRecords(@PathVariable("id") Integer id, Model model, @ModelAttribute Records records) {
        this.idd = id;
        ReceivingEnter re=service.getById(id);
        model.addAttribute("receivingEnter", re);
        model.addAttribute("recNameList", service.recordNames(re.getOrderBond().getId()));
        model.addAttribute("listRecords", service.listAllRecords(id));
        model.addAttribute("estList", estService.listAll());
        return "receivingEnterToAdd_List";
    }

    @PostMapping("/saveRecordToReceivingEnter")
    public String saveRecordToReceivingEnter(@ModelAttribute("records") Records record, @RequestParam("count") Integer count) {
        ReceivingEnter receivingEnter = service.getById(idd);
        OrderBond orderBond=receivingEnter.getOrderBond();
        record.setReceiving_enter(receivingEnter);
        record.setOrderBond(receivingEnter.getOrderBond());
        record.setServices(receivingEnter.getServicesReceivingEnter());
        record.setRecorddate(receivingEnter.getReceive_enter_date());
        RecordName rn = recNameService.getOneRn(record.getRecordName().getId());
        rn.setCount(count);
        recService.save(record);
        recNameService.save(rn);
        orderBond.setState(stateOfOrderBond(orderBond));
        ordService.save(orderBond);

        return "redirect:/findReceivingEnterToAddRecords/id=" + idd;

    }

    private String stateOfOrderBond(OrderBond orderBond) {
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

    @GetMapping("/findReceivingEnterRecord")
    @ResponseBody
    public Records findReceivingEnterRecord(Integer id) {
        Records record = recService.findRecord(id);
        Integer count = recService.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }


    @GetMapping("/deleteReceivingEnterRecord")
    public String deleteReceivingEnterRecord(Integer id) {
        OrderBond o=recService.get(id).get().getReceiving_enter().getOrderBond();
        recService.delete(id);
        o.setState(stateOfOrderBond(o));
        ordService.save(o);

        return "redirect:/findReceivingEnterToAddRecords/id=" + idd;
    }

    @GetMapping("/findLastAddOneEnter")
    @ResponseBody
    public Integer findLastAddOneEnter() {
        ReceivingEnter r = service.getLast();

        if (r != null)
            return r.getReceive_enter_num() + 1;
        else
            return 1;

    }


}
