package com.nchu.controller;

import com.nchu.entity.Department;
import com.nchu.entity.Employee;
import com.nchu.service.DepartmentService;
import com.nchu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    @Qualifier("departmentService")
    private DepartmentService departmentService;
    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    public static final Integer PAGE_SIZE=10;

    @RequestMapping("/register")
    public String register(HttpServletRequest request, Model model){
        List<Department> list = departmentService.queryAll();
        model.addAttribute("list",list);
        return "register";
    }
    @RequestMapping("/deReg")
    public String deReg(Employee employee, Model model){
        System.out.println(employee);
        boolean insert = employeeService.insert(employee);
        if(insert){
            return "redirect:/login";
        }else{
            return "register";
        }
    }

    @RequestMapping("/admin/searchemployees")
    public String searchemployees(Employee employee, @RequestParam(defaultValue = "1") Integer page, Model model){

        List<Employee> list = employeeService.queryAll(employee,page,PAGE_SIZE);
        Long total = employeeService.getTotal(employee);
        model.addAttribute("emps",list);
        model.addAttribute("page",page);
        model.addAttribute("total",total);

        model.addAttribute("pagenum",total%PAGE_SIZE==0?total/PAGE_SIZE:total/PAGE_SIZE+1);
        return "searchemployees";
    }
}
