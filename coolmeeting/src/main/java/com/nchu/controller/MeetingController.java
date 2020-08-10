package com.nchu.controller;

import com.nchu.entity.Department;
import com.nchu.entity.Employee;
import com.nchu.entity.Meeting;
import com.nchu.entity.Meetingroom;
import com.nchu.service.DepartmentService;
import com.nchu.service.EmployeeService;
import com.nchu.service.MeetingService;
import com.nchu.service.MeetingroomSercie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingroomSercie meetingroomSercie;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    private static final Integer PAGE_SIZE=10;

    @RequestMapping("/bookmeeting")
    public String bookmeeting(Model model){
        List<Meetingroom> meetingrooms = meetingroomSercie.queryAll1();
        model.addAttribute("meetingrooms",meetingrooms);
        return "bookmeeting";
    }

    @RequestMapping("/alldeps")
    @ResponseBody
    public List<Department> getAllDepartment(){
        return departmentService.queryAll();
    }
    @RequestMapping("/queryAllBydepId")
    @ResponseBody
    public List<Employee> getEmpByDepid(Integer depId){
        return employeeService.queryAllBydepId(depId);
    }
    @RequestMapping("/addmeeting")
    public String addMeeting(Meeting meeting,Integer[] mps, HttpSession session){
        System.out.println(meeting.getStarttime());
        Employee current = ((Employee) session.getAttribute("current"));
        meeting.setReservationistid(current.getEmployeeid());
        boolean insert = meetingService.insert(meeting,mps);
        if(insert){
            return "redirect:/searchmeetings";
        }
        return "addmeeting";
    }

    @RequestMapping("/searchmeetings")
    public String searchmeetings(Meeting meeting, @RequestParam(defaultValue = "1") Integer page, Model model){
        List<Meeting> list = meetingService.queryAll(meeting, page, PAGE_SIZE);
        Long total = meetingService.getTotal(meeting);
        model.addAttribute("meetings",list);
        model.addAttribute("page",page);
        model.addAttribute("total",total);
        System.out.println(list.get(0).getStarttime());
        model.addAttribute("pagenum",total%PAGE_SIZE==0?total/PAGE_SIZE:total/PAGE_SIZE+1);
        return "searchmeetings";
    }
    @RequestMapping("/meetingdetails")
    public String meetingdetails(){
        return "/meetingdetails";
    }

    @RequestMapping("/doGo")
    @ResponseBody
    public Integer doGo(Integer page,Integer pagenum){
        if(page<pagenum){
            page+=1;
        }
        return page;
    }
}
