package com.nchu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Notifications {
    @RequestMapping("/notifications")
    public String notifications(){
        return "notifications";
    }
}
