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
public class OS_orderBondController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Autowired
    OS_orderBondService service;
    @Autowired
    private establishmentService estService;
    @Autowired
    private serviceService serservice;
    @Autowired
    private OS_recordsService recService;
    @Autowired
    private OS_recordNameService recNameService;
    // @Autowired
    // private OS_receivingEnterService rEnterService;

    @RequestMapping("/OS_orderBondList")
    public String OS_vieworderBond(Model model, @ModelAttribute("orderBond") OS_OrderBond orderBond) {
        List<OS_OrderBond> list = service.listAll();
        model.addAttribute("orderBondList", list);
        model.addAttribute("serOrderBondList", serservice.listAll1());
        model.addAttribute("estList", estService.listAll());
        return "OS_orderBond_List";
    }

    @PostMapping("/OS_saveOrderBond")
    public String OS_saveOrderBond(@ModelAttribute OS_OrderBond orderBond, @RequestParam("file") MultipartFile file,
                                @RequestParam(value = "fName", required = false) String fName,
                                @RequestParam(value = "serviceAdd", required = false) String serviceAdd) throws IOException {


        if (serviceAdd.length()!=0 ) {
            Services service = new Services();
            service.setServicename(serviceAdd);
            serservice.save(service);
            orderBond.setServicesOrderBond(service);
        }

        if (file.isEmpty()) {
            if (fName != "") {
                orderBond.setFileName(fName);
            } else {
                orderBond.setFileName("");
            }
        } else {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
            Date date = new Date();
            String s = formatter.format(date);
            // String fileName = file.getOriginalFilename();
            String fNameWithDate = fName + s + ".jpg";
            String filePath = Paths.get(uploadDirectory, fNameWithDate).toString();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(file.getBytes());
            stream.close();
            orderBond.setFileName(fNameWithDate);
        }


        service.save(orderBond);


        return "redirect:/OS_orderBondList";

    }

    @GetMapping("/OS_removeFileOrederBond/{id}/{deleteFileName}")
    public String OS_removeFileOrederBond(@PathVariable("id") Integer id, @PathVariable("deleteFileName") String deleteFileName) {
        String path = null;
        File file = null;
        try {
            path = uploadDirectory + "/" + deleteFileName;
            file = new File(path);
            if (file.exists()) {
                service.deleteFile(id, deleteFileName);
                return "redirect:/OS_orderBondList";
            } else {
                return "redirect:/OS_orderBondList";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyFileNotFoundException("الصورة غير موجودة: " + deleteFileName);
        }
    }


    @GetMapping("/OS_deleteOrderBond")
    public String OS_deleteOrderBond(Integer id) {
        service.delete(id);
        return "redirect:/OS_orderBondList";
    }

    @GetMapping("/OS_findOrderBond")
    @ResponseBody
    public Optional<OS_OrderBond> OS_findOrderBond(Integer id) {
        return service.get(id);
    }


    @GetMapping("/OS_findLastAddOneOrder")
    @ResponseBody
    public Integer OS_findLastAddOneOrder() {
        OS_OrderBond r = service.getLast();

        if (r != null)
            return r.getOrderNum() + 1;
        else
            return 1;

    }


    //------------------------------------------------ OrderBondRecords -------------------------------------------//


    int idd;

    @GetMapping("/OS_orderBondRecords/id={id}")
    public String OS_getRecordsOfOrder(@PathVariable("id") Integer id, Model model, @ModelAttribute OS_Records records) {
        this.idd = id;
        OS_OrderBond orderBond = service.getById(id);
        List<OS_Records> listR = service.listAllRecordsOrederBond(orderBond.getId());
        model.addAttribute("records", new OS_Records());
        model.addAttribute("recordsOrderBondList", listR);
        model.addAttribute("recNameList", recNameService.listAll());
        model.addAttribute("estList", estService.listAll());
        model.addAttribute("orderBond", service.getById(id));

        return "OS_orderBondRecords";
    }


    @PostMapping("/OS_saveOrderBondRecord")
    public String OS_saveOrderBondRecord(@ModelAttribute("records") OS_Records record) {
        OS_OrderBond orderBond = service.getById(idd);
        record.setOrderBond(orderBond);
        record.setRecorddate(orderBond.getOrderDate());
        record.setServices(orderBond.getServicesOrderBond());
        recService.save(record);
        orderBond.setState(OS_stateOfOrderBond(orderBond));
        service.save(orderBond);
        return "redirect:/OS_orderBondRecords/id=" + idd;

    }

    private String OS_stateOfOrderBond(OS_OrderBond orderBond) {
        if (service.listAllRecordsReceivingEnter(orderBond.getId()).size() == 0) {
            return "لم يتم التسليم بعد";
        } else {
            if (service.stateOrderBond(orderBond.getId()) == 0) {
                return "تم تسليم كل المطبوعات";
            } else {
                return "تسليم جزئي";
            }
        }
    }

    @GetMapping("/OS_findOrderBondRecord")
    @ResponseBody
    public OS_Records OS_findOrderBondRecord(Integer id) {
        OS_Records record = recService.findRecord(id);
        Integer count = recService.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }

    @GetMapping("/OS_deleteOrderBondRecord")
    public String OS_deleteOrderBondRecord(Integer id) {
        OS_Records r = recService.get(id).get();
        OS_OrderBond o = r.getOrderBond();
        recService.delete(id);
        o.setState(OS_stateOfOrderBond(o));
        service.save(o);
        return "redirect:/OS_orderBondRecords/id=" + idd;
    }

}
