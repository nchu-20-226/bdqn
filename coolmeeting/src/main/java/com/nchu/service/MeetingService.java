package com.nchu.service;

import com.nchu.entity.Meeting;

import java.util.List;

public interface MeetingService {
    List<Meeting> queryAllByStatus(Integer status);
    List<Meeting> queryAll1();
    boolean insert(Meeting meeting,Integer[] mps);


    List<Meeting> queryAll(Meeting meeting,Integer page, Integer size);
    Long getTotal(Meeting meeting);
}
