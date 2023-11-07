package com.dcfB.controller;

import com.dcfB.model.*;
import com.dcfB.service.OS_recordNameService;
import com.dcfB.service.establishmentService;
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
import java.util.List;
import java.util.Optional;

@Controller
public class OS_recordNameController {

    @Autowired
    private OS_recordNameService service;
    @Autowired
    private establishmentService estService;


    @RequestMapping("/OS_recordNameList")
    public String OS_viewRecordName(Model model) {
        List<Establishment> estList = estService.listAll();
        model.addAttribute("estList", estList);

        List<OS_RecordName> recordList = service.listAll();
        model.addAttribute("recordList", recordList);
        return "OS_recordName_List";
    }

    OS_RecordName rcnm = null;

    @PostMapping("/OS_save")
    public String OS_saveRecordName(@ModelAttribute OS_RecordName rName) {
        if (rcnm != null){
            rName.setCount(rcnm.getCount());
            rName.setSafetyStock(rcnm.getSafetyStock());
        }
        rcnm = null;
        if (rName.getCount()==null || rName.getCount().toString().equals(""))
            rName.setCount(0);
        if (rName.getSafetyStock()==null || rName.getSafetyStock().toString().equals(""))
            rName.setSafetyStock(0);
        service.save(rName);
        return "redirect:/OS_recordNameList";

    }


    @GetMapping("/OS_delete")
    public String OS_deleteRecordName(Long id) {
        service.delete(id);
        return "redirect:/OS_recordNameList";
    }

    @GetMapping("/OS_findOne")
    @ResponseBody
    public Optional<OS_RecordName> OS_findOne(Long id) {
        Optional<OS_RecordName> r = service.get(id);
        rcnm = r.get();
        return r;
    }


    @GetMapping("/OS_getSafetyStock/id={id}")
    @ResponseBody
    public Integer OS_getSafetyStock(@PathVariable Long id) {
        OS_RecordName recordName = service.getOneRn(id);
        Integer stock = recordName.getSafetyStock();
        return stock;
    }


    @RequestMapping("/OS_safetyStock")
    public String OS_safetyStock(Model model, @ModelAttribute("records") OS_Records records) {

        List<OS_RecordName> rnList = service.listAll();

        List<Establishment> estList = estService.listAll();
        model.addAttribute("estList", estList);
        model.addAttribute("recordsList", rnList);

        return "OS_safetyStockList";
    }


    OS_RecordName rc;

    @PostMapping("/OS_saveStockRec")
    public String OS_saveStockRec(@ModelAttribute OS_RecordName recordName) {
        rc.setSafetyStock(recordName.getSafetyStock());
        service.save(rc);
        return "redirect:/OS_safetyStock";
    }


    @GetMapping("/OS_findStockRec")
    @ResponseBody
    public Optional<OS_RecordName> OS_findStockRec(Long id) {

        Optional<OS_RecordName> recrdN = service.get(id);
        rc = recrdN.get();
        return recrdN;
    }

    @RequestMapping("/OS_safetyStockReport")
    public void OS_generateSafetyStockReport(HttpServletResponse response) throws IOException, JRException {
        List<OS_RecordName> stockList = service.listAll();
        response.setContentType("text/html; charset=UTF-8");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(stockList);
        InputStream inputStream = this.getClass().getResourceAsStream("/OS_saftyStock.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        HtmlExporter htmlExporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        htmlExporter.exportReport();

    }
}
