package com.nchu.controller;

import com.nchu.entity.Department;
import com.nchu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @RequestMapping("/departments")
    public String departments(Model model){
        model.addAttribute("deps",departmentService.queryAll());
        return "departments";
    }

    @RequestMapping("/adddepartment")
    public String addDepartment(Department department){

        departmentService.insert(department);
        return "redirect:/admin/departments";
    }
    @RequestMapping("/deldepartment")
    public String delDepartment(Integer departmentid){

        departmentService.deleteById(departmentid);
        return "redirect:/admin/departments";
    }

    @RequestMapping("/updatedep")
    @ResponseBody
    public String updatedep(Integer id, String name) {
        boolean result = departmentService.updatedep(id, name);
        if (result) {
            return "success";
        }
        return "error";
    }
}
