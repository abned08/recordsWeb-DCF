package com.dcfB.controller;

import com.dcfB.model.Establishment;
import com.dcfB.model.RecordName;
import com.dcfB.service.establishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class establishmentController {

    @Autowired
    private establishmentService service;




    @PostMapping("/saveEst")
    public String saveEstablishment ( Establishment establishment) {

        service.save(establishment);
        return "redirect:/home";


    }

}
