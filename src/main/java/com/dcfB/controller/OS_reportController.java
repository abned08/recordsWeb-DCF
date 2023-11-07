package com.dcfB.controller;

import com.dcfB.model.Establishment;
import com.dcfB.model.OS_Report;
import com.dcfB.service.OS_reportService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class OS_reportController {

    @Autowired
    OS_reportService rpservice;
    @Autowired
    private establishmentService estService;

    String dateR="";

    @RequestMapping(value = "/OS_reportList")
    public String OS_viewReport(Model model) {
        List<Establishment> estList=estService.listAll();
        model.addAttribute("estList", estList);
        List<OS_Report> reportList = new ArrayList<>();
        if (dateR.equals(""))
            rpservice.deleteAll();
        else{
            reportList = rpservice.listAll();
            model.addAttribute("dateOfReport", dateR);
        }
        model.addAttribute("reportList", reportList);
        model.addAttribute("title","التقـــرير");
        return "OS_report_List";
    }


    @PostMapping("/OS_dateReport")
    public String OS_showReportByDay(HttpServletResponse response,@RequestParam(name = "dateReport") String dateReport) {
        rpservice.fillReport(dateReport);
        dateR=dateReport;
        return "redirect:/OS_reportList";
    }


    @PostMapping("/OS_saveRep")
    public String OS_saveReport(OS_Report report) {
        rpservice.save(report);
        return "redirect:/OS_reportList";
    }

    @GetMapping("/OS_findRep")
    @ResponseBody
    public Optional<OS_Report> OS_findReport(Long id) {
        return rpservice.get(id);
    }

    final ModelAndView model = new ModelAndView();

    @RequestMapping("/OS_report")
    public void OS_generateReport(HttpServletResponse response) throws IOException, JRException {
        List<OS_Report> reportList = rpservice.listAll();
        response.setContentType("text/html; charset=UTF-8");
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(reportList);
        InputStream inputStream=this.getClass().getResourceAsStream("/OS_records.jrxml");
        JasperReport jasperReport=JasperCompileManager.compileReport(inputStream);
        Map<String, Object> parameters = new HashMap<>();


        parameters.put("dateReport", dateR);
        parameters.put("establishment", estService.getLast().getEstname());

        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        HtmlExporter htmlExporter=new HtmlExporter(DefaultJasperReportsContext.getInstance());
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        htmlExporter.exportReport();

    }

}
