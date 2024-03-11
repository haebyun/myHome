package com.studyproject.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyHomeController {

    @GetMapping
    public String index() {
        return "index";
    }
}
