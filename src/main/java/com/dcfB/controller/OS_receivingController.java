package com.dcfB.controller;

import com.dcfB.model.*;
import com.dcfB.service.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class OS_receivingController {
    
    @Autowired
    OS_receivingService service;
    @Autowired
    serviceService serservice;
    @Autowired
    OS_recordsService recService;
    @Autowired
    OS_recordNameService recNameService;
    @Autowired
    private establishmentService estService;

    @RequestMapping("/OS_receivingList")
    public String OS_viewReceiving(Model model, @ModelAttribute("receiving") OS_Receiving receiving) {
        List<OS_Receiving> list=service.listAll();
        model.addAttribute("receivList", list);
        model.addAttribute("serReceivingNameList",serservice.listAll());
        model.addAttribute("estList", estService.listAll());
        return "OS_receivings_List";
    }


    @PostMapping("/OS_saveReceiving")
    public String OS_saveReceiving ( OS_Receiving receiving) {
        Integer r=receiving.getMemo_num() ==null ? 0 : receiving.getMemo_num();
        receiving.setMemo_num(r);
        service.save(receiving);
        return "redirect:/OS_receivingList";

    }


    @GetMapping("/OS_deleteReceiving")
    public String OS_deleteReceiving (Integer id) {
        service.delete(id);
        return "redirect:/OS_receivingList";
    }

    @GetMapping("/OS_findReceiving")
    @ResponseBody
    public Optional<OS_Receiving> OS_findReceiving (Integer id) {
        return service.get(id);
    }

    @GetMapping("/OS_findLastAddOne")
    @ResponseBody
    public Integer OS_findLastAddOne () {
        OS_Receiving r=service.getLast();

        if (r!=null)
            return r.getReceive_num()+1;
        else
            return 1;

    }

    ///////_______________________*******______________________________receivingToAddController

    int idd;
    @RequestMapping("/OS_findReceivingToAddRecords/id={id}")
    public String OS_findReceivingToAddRecords (@PathVariable("id") int id, Model model, @ModelAttribute OS_Records records) {
        this.idd=id;
        model.addAttribute("records",new OS_Records());
        model.addAttribute("receiving",service.getById(id));
        model.addAttribute("recNameList",recNameService.listAll());
        model.addAttribute("listRecords",service.listAllRecords(id));
        model.addAttribute("estList", estService.listAll());
        return "OS_receivingToAdd_List";
    }

    @PostMapping("/OS_saveRecordToReceiving")
    public  String OS_saveRecordToReceiving (@ModelAttribute("records") OS_Records record, @RequestParam("count")Integer count) {
        OS_Receiving receiving=service.getById(idd);
        record.setReceiving(receiving);
        record.setServices(receiving.getServicesReceiving());
        record.setRecorddate(receiving.getReceive_date());
        OS_RecordName rn=recNameService.getOneRn(record.getRecordName().getId());
        rn.setCount(count);
        recService.save(record);
        recNameService.save(rn);
        return "redirect:/OS_findReceivingToAddRecords/id="+idd;

    }

    @GetMapping("/OS_findReceivingRecord")
    @ResponseBody
    public OS_Records OS_findReceivingRecord (Integer id) {
        OS_Records record=recService.findRecord(id);
        Integer count= recService.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }


    @GetMapping("/OS_deleteReceivingRecord")
    public String OS_deleteReceivingRecord (Integer id) {
        recService.delete(id);
        return "redirect:/OS_findReceivingToAddRecords/id="+idd;
    }


    @RequestMapping("/OS_reportReceiving")
    public void OS_generateReportReceiving(HttpServletResponse response) throws IOException, JRException {
        OS_Receiving receiving=service.getById(idd);
        List<OS_Records>listRecords=service.listAllRecords(idd);
        response.setContentType("text/html; charset=UTF-8");
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(Collections.singleton(receiving));
        JRBeanCollectionDataSource SubReportData=new JRBeanCollectionDataSource(listRecords);
        InputStream inputStream=getClass().getResourceAsStream("/OS_recordsDGDN.jrxml");
        InputStream inputStreamSub=getClass().getResourceAsStream("/OS_sub.jrxml");
        JasperReport jasperReport= JasperCompileManager.compileReport(inputStream);
        JasperReport jasperReport1= JasperCompileManager.compileReport(inputStreamSub);

        Map<String, Object> parameters = new HashMap<>();

        Integer id=receiving.getId();
        parameters.put("rn", id);
        parameters.put("JasperCustomSubReportDatasource",SubReportData);
        parameters.put("JasperCustomSubReportLocation",jasperReport1);
        parameters.put("establishment", estService.getLast().getEstname());
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        HtmlExporter htmlExporter=new HtmlExporter(DefaultJasperReportsContext.getInstance());
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        htmlExporter.exportReport();


    }

}
