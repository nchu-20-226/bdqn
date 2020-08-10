package com.nchu.service;

import com.nchu.entity.Meetingroom;

import java.util.List;

public interface MeetingroomSercie {
    List<Meetingroom> queryAll1();
    Meetingroom queryById(Integer roomid);
    boolean update(Meetingroom meetingroom);
    boolean insert(Meetingroom meetingroom);
}
