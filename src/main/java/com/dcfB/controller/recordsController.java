package com.dcfB.controller;

import com.dcfB.model.*;
import com.dcfB.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class recordsController {

    @Autowired
    private recordsService service;
    @Autowired
    private recordNameService rnService;
    @Autowired
    private serviceService srService;
    @Autowired
    private establishmentService estService;
    @Autowired
    private orderBondService ordService;


    @RequestMapping(value = "/")
    public String method() {

        if (service.listAll().isEmpty()) {
            return "redirect:/homeInit";
        } else {
            return "redirect:/home";
        }
    }


    @RequestMapping("/home")
    public String viewHome(Model model) {
        List<Establishment> estList = estService.listAll();
        model.addAttribute("estList", estList);
        return "home";
    }

    @RequestMapping("/homeInit")
    public String viewHomeInit() {
        return "homeInit";
    }


    @RequestMapping("/recordsList")
    public <bindingResult> String viewRecords(Model model, @ModelAttribute("records") Records records, BindingResult bindingResult) {

        List<Records> recordsList = service.listAll();
        List<Establishment> estList = estService.listAll();
        model.addAttribute("estList", estList);
        model.addAttribute("recNameList", rnService.listAll());
        model.addAttribute("serNameList", srService.listAll());
        model.addAttribute("recordsList", recordsList);
        model.addAttribute("title","التسجـيلات");

//        model.addAttribute("count",count);

        return "records_List";
    }


    @PostMapping("/saveRec")
    public String saveRecords(@RequestParam(required = false, value = "recIn") String recIn, @RequestParam(required = false, value = "count") int count, @ModelAttribute Records records, Model model, int quantity) {


        if (recIn != null) {
            records.setRecordin(quantity);
            records.setRecordout(0);

        } else {
            records.setRecordout(quantity);
            records.setRecordin(0);

        }
        model.addAttribute("quantity", quantity);
        service.save(records);
        RecordName recordName = records.getRecordName();
        recordName.setCount(count);
        rnService.save(recordName);
        return "redirect:/recordsList";
    }

    @RequestMapping("/initialList")
    public String viewList(Model model) {
        if (service.listAll().isEmpty()) {
            List<Records> init = service.initRecordsList();
            RecordsInit ri = new RecordsInit(init);
            ri.setRecordsInitList(init);

            model.addAttribute("initRecordsList", ri);
            return "initial_List";
        } else {
            return "redirect:/home";
        }

    }


    @PostMapping("/saveInitRec")
    public String saveInitRecord(@ModelAttribute RecordsInit initRecordsList) {
//        List<Records> rec = new ArrayList<>();
//        service.listAll().iterator().forEachRemaining(rec::add);

        for (Records r : initRecordsList.getRecordsInitList()) {
            Long rnId = r.getRecordName().getId();
            RecordName rn = rnService.getOneRn(rnId);
            rn.setSafetyStock(r.getRecordName().getSafetyStock());
            rn.setCount(r.getRecordin());
            rnService.save(rn);
        }
        service.saveAll(initRecordsList.getRecordsInitList());
        return "redirect:/home";
    }

    @GetMapping("/deleteRec")
    public String deleteRecords(Integer id) {
        service.delete(id);
        return "redirect:/recordsList";
    }

    @GetMapping("/findRec")
    @ResponseBody
    public Records findRecords(Integer id) {
        Records record = service.findRecord(id);
        Integer count = service.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }

    @GetMapping("/countRecord/id={id}")
    @ResponseBody
    public Integer countRecord(@PathVariable Long id) {
        if (id==null) {
            id= Long.valueOf(0);
        }
        RecordName recordName = rnService.getOneRn(id);
        Integer count = service.sum(recordName.getId());
        return count;
    }

    @GetMapping("/countRecordOrderBond/id={id}/idO={idO}")
    @ResponseBody
    public Integer countRecordOrderBond(@PathVariable("id") Long id, @PathVariable("idO") Integer idO) {
        if (id==null) {
            id= Long.valueOf(0);
        }
        RecordName recordName = rnService.getOneRn(id);
        OrderBond orderBond = ordService.getById(idO);
        Integer count = service.sumOrderBond(recordName.getId(), orderBond.getId());


        return count;
    }


}
