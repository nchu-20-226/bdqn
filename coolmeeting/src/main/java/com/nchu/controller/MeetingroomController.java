package com.nchu.controller;

import com.nchu.entity.Meetingroom;
import com.nchu.service.MeetingroomSercie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MeetingroomController {
    @Autowired
    private MeetingroomSercie meetingroomSercie;
    @RequestMapping("/meetingrooms")
    public String meetingRooms(Model model){
        List<Meetingroom> rooms = meetingroomSercie.queryAll1();
        model.addAttribute("rooms",rooms);
        return "meetingrooms";
    }

    @RequestMapping("/roomdetails")
    public String roomdetails(Integer roomid,Model model){
        Meetingroom meetingroom = meetingroomSercie.queryById(roomid);
        model.addAttribute("meetingroom",meetingroom);
        return "roomdetails";
    }
    @RequestMapping("/updateMeetingroom")
    public String updateMeetingroom(Meetingroom meetingroom){
        boolean update = meetingroomSercie.update(meetingroom);
        return "redirect:/meetingrooms";
    }
    @RequestMapping("/admin/addmeetingroom")
    public String addmeetingroom(){
        return "addmeetingroom";
    }
    @RequestMapping("/admin/doAddmeetingroom")
    public String doAddmeetingroom(Meetingroom meetingroom){
        boolean insert = meetingroomSercie.insert(meetingroom);
        if(insert){
            return "redirect:/meetingrooms";
        }
        return "addmeetingroom";
    }

}
