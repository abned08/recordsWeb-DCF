package com.dcfB.controller;

import com.dcfB.model.Establishment;
import com.dcfB.model.Report;
import com.dcfB.service.establishmentService;
import com.dcfB.service.reportService;
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
public class reportController {

    @Autowired
    reportService rpservice;
    @Autowired
    private establishmentService estService;

    String dateR="";
    String dateREnd="";

    @RequestMapping(value = "/reportList")
    public String viewReport(Model model) {
        List<Establishment> estList=estService.listAll();
        model.addAttribute("estList", estList);
        List<Report> reportList =  new ArrayList<>();
        if (dateR.equals(""))
            rpservice.deleteAll();
        else{
            reportList = rpservice.listAll();
            model.addAttribute("dateOfReport", dateR);
            model.addAttribute("dateOfReportEnd", dateREnd);
        }
        model.addAttribute("reportList", reportList);
        model.addAttribute("title","التقـــرير");
        return "report_List";
    }


    @PostMapping("/dateReport")
    public String showReportByDay(HttpServletResponse response,@RequestParam(name = "dateReport") String dateReport, @RequestParam(name = "dateReportEnd") String dateReportEnd) {
        rpservice.fillReport(dateReport, dateReportEnd);
        dateR=dateReport;
        dateREnd= dateReportEnd;
        return "redirect:/reportList";
    }


    @PostMapping("/saveRep")
    public String saveReport(Report report) {
        rpservice.save(report);
        return "redirect:/reportList";
    }

    @GetMapping("/findRep")
    @ResponseBody
    public Optional<Report> findReport(Long id) {
        return rpservice.get(id);
    }

    final ModelAndView model = new ModelAndView();

    @RequestMapping("/report")
    public void generateReport(HttpServletResponse response) throws IOException, JRException {
        List<Report> reportList = rpservice.listAll();
        response.setContentType("text/html; charset=UTF-8");
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(reportList);
        InputStream inputStream=this.getClass().getResourceAsStream("/records.jrxml");
        JasperReport jasperReport=JasperCompileManager.compileReport(inputStream);
        Map<String, Object> parameters = new HashMap<>();


        parameters.put("dateReport", dateR);
        parameters.put("dateReportEnd", dateREnd);
        parameters.put("establishment", estService.getLast().getEstname());

        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        HtmlExporter htmlExporter=new HtmlExporter(DefaultJasperReportsContext.getInstance());
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        htmlExporter.exportReport();

    }

}
