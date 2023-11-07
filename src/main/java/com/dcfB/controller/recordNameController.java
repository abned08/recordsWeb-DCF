package com.dcfB.controller;

import com.dcfB.model.Establishment;
import com.dcfB.model.RecordName;
import com.dcfB.model.Records;
import com.dcfB.service.establishmentService;
import com.dcfB.service.recordNameService;
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
public class recordNameController {

    @Autowired
    private recordNameService service;
    @Autowired
    private establishmentService estService;


    @RequestMapping("/recordNameList")
    public String viewRecordName(Model model) {
        List<Establishment> estList = estService.listAll();
        model.addAttribute("estList", estList);

        List<RecordName> recordList = service.listAll();
        model.addAttribute("recordList", recordList);
        model.addAttribute("title","المطبــوعات");
        return "recordName_List";
    }


    RecordName rcnm = null;

    @PostMapping("/save")
    public String saveRecordName(@ModelAttribute RecordName rName) {
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
        return "redirect:/recordNameList";

    }


    @GetMapping("/delete")
    public String deleteRecordName(Long id) {
        service.delete(id);
        return "redirect:/recordNameList";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Optional<RecordName> findOne(Long id) {
        Optional<RecordName> r = service.get(id);
        rcnm = r.get();
        return r;
    }


    @GetMapping("/getSafetyStock/id={id}")
    @ResponseBody
    public Integer getSafetyStock(@PathVariable Long id) {
        RecordName recordName = service.getOneRn(id);
        Integer stock = recordName.getSafetyStock();
        return stock;
    }


    @RequestMapping("/safetyStock")
    public String safetyStock(Model model, @ModelAttribute("records") Records records) {

        List<RecordName> rnList = service.listAll();

        List<Establishment> estList = estService.listAll();
        model.addAttribute("estList", estList);
        model.addAttribute("recordsList", rnList);

        return "safetyStockList";
    }


    RecordName rc;

    @PostMapping("/saveStockRec")
    public String saveStockRec(@ModelAttribute RecordName recordName) {
        rc.setSafetyStock(recordName.getSafetyStock());
        service.save(rc);
        return "redirect:/safetyStock";
    }


    @GetMapping("/findStockRec")
    @ResponseBody
    public Optional<RecordName> findStockRec(Long id) {

        Optional<RecordName> recrdN = service.get(id);
        rc = recrdN.get();
        return recrdN;
    }

    @RequestMapping("/safetyStockReport")
    public void generateSafetyStockReport(HttpServletResponse response) throws IOException, JRException {
        List<RecordName> stockList = service.listAll();
        response.setContentType("text/html; charset=UTF-8");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(stockList);
        InputStream inputStream = this.getClass().getResourceAsStream("/saftyStock.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        HtmlExporter htmlExporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        htmlExporter.exportReport();

    }
}
