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
public class orderBondController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Autowired
    orderBondService service;
    @Autowired
    private establishmentService estService;
    @Autowired
    private serviceService serservice;
    @Autowired
    private recordsService recService;
    @Autowired
    private recordNameService recNameService;
    // @Autowired
    // private receivingEnterService rEnterService;

    @RequestMapping("/orderBondList")
    public String vieworderBond(Model model, @ModelAttribute("orderBond") OrderBond orderBond) {
        List<OrderBond> list = service.listAll();
        model.addAttribute("orderBondList", list);
        model.addAttribute("serOrderBondList", serservice.listAll());
        model.addAttribute("estList", estService.listAll());
        model.addAttribute("title","سند الطلبية");
        return "orderBond_List";
    }

    @PostMapping("/saveOrderBond")
    public String saveOrderBond(@ModelAttribute OrderBond orderBond, @RequestParam("file") MultipartFile file,
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


        return "redirect:/orderBondList";

    }

    @GetMapping("/removeFileOrederBond/{id}/{deleteFileName}")
    public String removeFileOrederBond(@PathVariable("id") Integer id, @PathVariable("deleteFileName") String deleteFileName) {
        String path = null;
        File file = null;
        try {
            path = uploadDirectory + "/" + deleteFileName;
            file = new File(path);
            if (file.exists()) {
                service.deleteFile(id, deleteFileName);
                return "redirect:/orderBondList";
            } else {
                return "redirect:/orderBondList";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyFileNotFoundException("الصورة غير موجودة: " + deleteFileName);
        }
    }


    @GetMapping("/deleteOrderBond")
    public String deleteOrderBond(Integer id) {
        service.delete(id);
        return "redirect:/orderBondList";
    }

    @GetMapping("/findOrderBond")
    @ResponseBody
    public Optional<OrderBond> findOrderBond(Integer id) {
        return service.get(id);
    }


    @GetMapping("/findLastAddOneOrder")
    @ResponseBody
    public Integer findLastAddOneOrder() {
        OrderBond r = service.getLast();

        if (r != null)
            return r.getOrderNum() + 1;
        else
            return 1;

    }


    //------------------------------------------------ OrderBondRecords -------------------------------------------//


    int idd;

    @GetMapping("/orderBondRecords/id={id}")
    public String getRecordsOfOrder(@PathVariable("id") Integer id, Model model, @ModelAttribute Records records) {
        this.idd = id;
        OrderBond orderBond = service.getById(id);
        List<Records> listR = service.listAllRecordsOrederBond(orderBond.getId());
        model.addAttribute("recordsOrderBondList", listR);
        model.addAttribute("recNameList", recNameService.listAll());
        model.addAttribute("estList", estService.listAll());
        model.addAttribute("orderBond", service.getById(id));

        return "orderBondRecords";
    }


    @PostMapping("/saveOrderBondRecord")
    public String saveOrderBondRecord(@ModelAttribute("records") Records record) {
        OrderBond orderBond = service.getById(idd);
        record.setOrderBond(orderBond);
        record.setRecorddate(orderBond.getOrderDate());
        record.setServices(orderBond.getServicesOrderBond());
        recService.save(record);
        orderBond.setState(stateOfOrderBond(orderBond));
        service.save(orderBond);
        return "redirect:/orderBondRecords/id=" + idd;

    }

    private String stateOfOrderBond(OrderBond orderBond) {
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

    @GetMapping("/findOrderBondRecord")
    @ResponseBody
    public Records findOrderBondRecord(Integer id) {
        Records record = recService.findRecord(id);
        Integer count = recService.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }

    @GetMapping("/deleteOrderBondRecord")
    public String deleteOrderBondRecord(Integer id) {
        Records r = recService.get(id).get();
        OrderBond o = r.getOrderBond();
        recService.delete(id);
        o.setState(stateOfOrderBond(o));
        service.save(o);
        return "redirect:/orderBondRecords/id=" + idd;
    }

}
