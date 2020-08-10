package com.nchu.entity;

import java.io.Serializable;

/**
 * (Meetingroom)实体类
 *
 * @author makejava
 * @since 2020-08-07 10:21:21
 */
public class Meetingroom implements Serializable {
    private static final long serialVersionUID = -25945752398042263L;
    
    private Integer roomid;
    
    private Integer roomnum;
    
    private String roomname;
    
    private Integer capacity;
    
    private Integer status;
    
    private String description;

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public Integer getRoomnum() {
        return roomnum;
    }

    public void setRoomnum(Integer roomnum) {
        this.roomnum = roomnum;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Meetingroom{" +
                "roomid=" + roomid +
                ", roomnum=" + roomnum +
                ", roomname='" + roomname + '\'' +
                ", capacity=" + capacity +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}