package com.nchu.controller;

import com.nchu.entity.Employee;
import com.nchu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;



    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/doLogin")
    public String doLogin(String username, String password, Model model, HttpSession session){
        Employee employee = employeeService.doLogin(username, password);
        if(employee==null){
            model.addAttribute("error","用户名或密码错误");
            return "login";
        }else{
            if(((employee.getStatus()) == 0) || (employee.getStatus() == 2)){
                model.addAttribute("error","用户审批未通过");
                return "login";
            }else{
                session.setAttribute("current",employee);
                return "redirect:/notifications";
            }
        }
    }

    @RequestMapping("/doLogout")
    public String doLogout(HttpSession session){
        session.removeAttribute("current");
        return "redirect:/login";
    }





}
