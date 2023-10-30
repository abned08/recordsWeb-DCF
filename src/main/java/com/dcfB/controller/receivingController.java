package com.dcfB.controller;

import com.dcfB.model.Receiving;
import com.dcfB.model.RecordName;
import com.dcfB.model.Records;
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
public class receivingController {
    
    @Autowired
    receivingService service;
    @Autowired
    serviceService serservice;
    @Autowired
    recordsService recService;
    @Autowired
    recordNameService recNameService;
    @Autowired
    private establishmentService estService;

    @RequestMapping("/receivingList")
    public String viewReceiving(Model model, @ModelAttribute("receiving") Receiving receiving) {
        List<Receiving> list=service.listAll();
        model.addAttribute("receivList", list);
        model.addAttribute("serReceivingNameList",serservice.listAll());
        model.addAttribute("estList", estService.listAll());
        model.addAttribute("title","بيانات التسليم");
        return "receivings_List";
    }


    @PostMapping("/saveReceiving")
    public String saveReceiving ( Receiving receiving) {
        Integer r=receiving.getMemo_num() ==null ? 0 : receiving.getMemo_num();
        receiving.setMemo_num(r);
        service.save(receiving);
        return "redirect:/receivingList";

    }


    @GetMapping("/deleteReceiving")
    public String deleteReceiving (Integer id) {
        service.delete(id);
        return "redirect:/receivingList";
    }

    @GetMapping("/findReceiving")
    @ResponseBody
    public Optional<Receiving> findReceiving (Integer id) {
        return service.get(id);
    }

    @GetMapping("/findLastAddOne")
    @ResponseBody
    public Integer findLastAddOne () {
        Receiving r=service.getLast();

        if (r!=null)
            return r.getReceive_num()+1;
        else
            return 1;

    }

    ///////_______________________*******______________________________receivingToAddController

    int idd;
    @RequestMapping("/findReceivingToAddRecords/id={id}")
    public String findReceivingToAddRecords (@PathVariable("id") int id, Model model, @ModelAttribute Records records) {
        this.idd=id;
        model.addAttribute("receiving",service.getById(id));
        model.addAttribute("recNameList",recNameService.listAll());
        model.addAttribute("listRecords",service.listAllRecords(id));
        model.addAttribute("estList", estService.listAll());
        return "receivingToAdd_List";
    }

    @PostMapping("/saveRecordToReceiving")
    public String saveRecordToReceiving (@ModelAttribute("records") Records record,@RequestParam("count")Integer count) {
        Receiving receiving=service.getById(idd);
        record.setReceiving(receiving);
        record.setServices(receiving.getServicesReceiving());
        record.setRecorddate(receiving.getReceive_date());
        RecordName rn=recNameService.getOneRn(record.getRecordName().getId());
        rn.setCount(count);
        recService.save(record);
        recNameService.save(rn);
        return "redirect:/findReceivingToAddRecords/id="+idd;

    }

    @GetMapping("/findReceivingRecord")
    @ResponseBody
    public Records findReceivingRecord (Integer id) {
        Records record=recService.findRecord(id);
        Integer count= recService.sum(record.getRecordName().getId());
        record.setCount(count);
        return record;
    }


    @GetMapping("/deleteReceivingRecord")
    public String deleteReceivingRecord (Integer id) {
        recService.delete(id);
        return "redirect:/findReceivingToAddRecords/id="+idd;
    }


    @RequestMapping("/reportReceiving")
    public void generateReportReceiving(HttpServletResponse response) throws IOException, JRException {
        Receiving receiving=service.getById(idd);
        List<Records>listRecords=service.listAllRecords(idd);
        response.setContentType("text/html; charset=UTF-8");
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(Collections.singleton(receiving));
        JRBeanCollectionDataSource SubReportData=new JRBeanCollectionDataSource(listRecords);
        InputStream inputStream=getClass().getResourceAsStream("/recordsDGDN.jrxml");
        InputStream inputStreamSub=getClass().getResourceAsStream("/sub.jrxml");
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
