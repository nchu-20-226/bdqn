package com.nchu.controller;

import com.nchu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 注册审批
 */
@Controller
@RequestMapping("/admin")
public class ApproveaccountController {
    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    private static final Integer STATUS=0;

    @RequestMapping("/approveaccount")
    public String approveaccount(Model model){
        model.addAttribute("emps",employeeService.getAllByStatus(STATUS));
        return "approveaccount";
    }
    @RequestMapping("/updateStatus")
    public String updateStatus(Integer employeeid,Integer status){
        boolean result = employeeService.update(employeeid, status);
        return "redirect:/admin/approveaccount";
    }
}
