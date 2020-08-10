package com.nchu.service;

import com.nchu.dao.MeetingDao;
import com.nchu.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingDao meetingDao;
    public List<Meeting> queryAllByStatus(Integer status) {
        return meetingDao.queryAllByStatus(status);
    }

    public List<Meeting> queryAll1() {
        return meetingDao.queryAll1();
    }

    public boolean insert(Meeting meeting,Integer[] mps) {
        meeting.setReservationtime(new Date());
        int insert = meetingDao.insert(meeting);
        int i = meetingDao.addParticipants(meeting.getMeetingid(), mps);
        if(insert>0&&i>0){
            return true;
        }
        else
            return false;
    }

    public List<Meeting> queryAll(Meeting meeting, Integer page, Integer size) {
        page=(page-1)*size;
        return meetingDao.queryAll(meeting,page,size);
    }

    public Long getTotal(Meeting meeting) {
        return meetingDao.getTotal(meeting);
    }
}
