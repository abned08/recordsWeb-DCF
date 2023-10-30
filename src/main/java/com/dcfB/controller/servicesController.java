package com.dcfB.controller;

import com.dcfB.model.Establishment;
import com.dcfB.model.RecordName;
import com.dcfB.model.Services;
import com.dcfB.service.establishmentService;
import com.dcfB.service.serviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class servicesController {

    @Autowired
    private serviceService service;
    @Autowired
    private establishmentService estService;

    @RequestMapping("/serviceList")
    public String viewService(Model model) {

        List<Establishment> estList=estService.listAll();
        model.addAttribute("estList", estList);
        List<Services> serviceList=service.listAll();
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("title","المصــــالح");
        return "service_List";
    }


    @RequestMapping("/OS_serviceList")
    public String OS_viewService(Model model) {

        List<Establishment> estList=estService.listAll();
        model.addAttribute("estList", estList);
        List<Services> serviceList=service.listAll();
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("title","المصــــالح");
        return "OS_service_List";
    }


    @PostMapping("/saveServ")
    public String saveService (@ModelAttribute Services services) {
        service.save(services);
        return "redirect:/serviceList";
    }


    @GetMapping("/deleteServ")
    public String deleteService (Integer id) {
        service.delete(id);
        return "redirect:/serviceList";
    }

    @GetMapping("/findServ")
    @ResponseBody
    public Optional<Services> findService (Integer id) {
        return service.get(id);
    }

}
