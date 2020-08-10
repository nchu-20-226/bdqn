package com.nchu.service;

import com.nchu.dao.MeetingroomDao;
import com.nchu.entity.Meetingroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MeetingroomSercieImpl implements MeetingroomSercie {
    @Autowired
    private MeetingroomDao meetingroomDao;
    public List<Meetingroom> queryAll1() {
       return meetingroomDao.queryAll1();
    }

    public Meetingroom queryById(Integer roomid) {
        return meetingroomDao.queryById(roomid);
    }

    public boolean update(Meetingroom meetingroom) {
        return meetingroomDao.update(meetingroom)>0;
    }

    public boolean insert(Meetingroom meetingroom) {
        return meetingroomDao.insert(meetingroom)>0;
    }
}
