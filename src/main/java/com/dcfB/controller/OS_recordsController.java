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
public class OS_recordsController {

    @Autowired
    private OS_recordsService service;
    @Autowired
    private OS_recordNameService rnService;
    @Autowired
    private serviceService srService;
    @Autowired
    private establishmentService estService;
    @Autowired
    private OS_orderBondService ordService;


    @RequestMapping(value = "/OS")
    public String method() {

        if (service.listAll().isEmpty()) {
            return "redirect:/OS_homeInit";
        } else {
            return "redirect:/OS_recordsList";
        }
    }


//    @RequestMapping("/home")
//    public String viewHome(Model model) {
//        List<Establishment> estList = estService.listAll();
//        model.addAttribute("estList", estList);
//        return "home";
//    }

    @RequestMapping("/OS_homeInit")
    public String OS_viewHomeInit() {
        return "OS_homeInit";
    }


    @RequestMapping("/OS_recordsList")
    public <bindingResult> String OS_viewRecords(Model model, @ModelAttribute("records") OS_Records records, BindingResult bindingResult) {

        List<OS_Records> recordsList = service.listAll();
        List<Establishment> estList = estService.listAll();
        model.addAttribute("estList", estList);
        model.addAttribute("recNameList", rnService.listAll());
        model.addAttribute("serNameList", srService.listAll());
        model.addAttribute("recordsList", recordsList);
//        model.addAttribute("count",count);

        return "OS_records_List";
    }


    @PostMapping("/OS_saveRec")
    public String OS_saveRecords(@RequestParam(required = false, value = "recIn") String recIn, @RequestParam(required = false, value = "count") int count, @ModelAttribute OS_Records records, Model model, int quantity) {


        if (recIn != null) {
            records.setRecordin(quantity);
            records.setRecordout(0);

        } else {
            records.setRecordout(quantity);
            records.setRecordin(0);

        }
        model.addAttribute("quantity", quantity);
        service.save(records);
        OS_RecordName recordName = records.getRecordName();
        recordName.setCount(count);
        rnService.save(recordName);
        return "redirect:/OS_recordsList";
    }

    @RequestMapping("/OS_initialList")
    public String OS_viewList(Model model) {
        if (service.listAll().isEmpty()) {
            List<OS_Records> init = service.initRecordsList();
            OS_RecordsInit ri = new OS_RecordsInit(init);
            ri.setRecordsInitList(init);

            model.addAttribute("initRecordsList", ri);
            return "OS_initial_List";
        } else {
            return "redirect:/OS_recordsList";
        }

    }


    @PostMapping("/OS_saveInitRec")
    public String OS_saveInitRecord(@ModelAttribute OS_RecordsInit initRecordsList) {
//        List<Records> rec = new ArrayList<>();
//        service.listAll().iterator().forEachRemaining(rec::add);

        for (OS_Records r : initRecordsList.getRecordsInitList()) {
            Long rnId = r.getRecordName().getId();
            OS_RecordName rn = rnService.getOneRn(rnId);
            rn.setSafetyStock(r.getRecordName().getSafetyStock());
            rn.setCount(r.getRecordin());
            rnService.save(rn);
        }
        service.saveAll(initRecordsList.getRecordsInitList());
        return "redirect:/OS_recordsList";
    }

    @GetMapping("/OS_deleteRec")
    public String OS_deleteRecords(Integer id) {
        service.delete(id);
        return "redirect:/OS_recordsList";
    }

    @GetMapping("/OS_findRec")
    @ResponseBody
    public OS_Records OS_findRecords(Integer id) {
        OS_Records record = service.findRecord(id);
        Integer count = service.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }

    @GetMapping("/OS_countRecord/id={id}")
    @ResponseBody
    public Integer OS_countRecord(@PathVariable Long id) {
        if (id==null) {
            id= Long.valueOf(0);
        }
        OS_RecordName recordName = rnService.getOneRn(id);
        Integer count = service.sum(recordName.getId());
        return count;
    }

    @GetMapping("/OS_countRecordOrderBond/id={id}/idO={idO}")
    @ResponseBody
    public Integer OS_countRecordOrderBond(@PathVariable("id") Long id, @PathVariable("idO") Integer idO) {
        if (id==null) {
            id= Long.valueOf(0);
        }
        OS_RecordName recordName = rnService.getOneRn(id);
        OS_OrderBond orderBond = ordService.getById(idO);
        Integer count = service.sumOrderBond(recordName.getId(), orderBond.getId());


        return count;
    }


}
