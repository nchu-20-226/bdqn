package com.nchu.dao;

import com.nchu.entity.Meeting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Meeting)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-07 09:59:27
 */
public interface MeetingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param meetingid 主键
     * @return 实例对象
     */
    Meeting queryById(Integer meetingid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Meeting> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param meeting 实例对象
     * @return 对象列表
     */
    List<Meeting> queryAll(@Param("meet") Meeting meeting,@Param("page")Integer page, @Param("size") Integer size);
    @Select("select * from meeting.meeting")
    List<Meeting> queryAll1();
    /**
     * 新增数据
     *
     * @param meeting 实例对象
     * @return 影响行数
     */
    int insert(Meeting meeting);
    int addParticipants(@Param("meetid") Integer meetid,@Param("mps") Integer[] mps);
    /**
     * 修改数据
     *
     * @param meeting 实例对象
     * @return 影响行数
     */
    int update(Meeting meeting);

    /**
     * 通过主键删除数据
     *
     * @param meetingid 主键
     * @return 影响行数
     */
    int deleteById(Integer meetingid);
    List<Meeting> queryAllByStatus(Integer status);
    Long getTotal(@Param("meet") Meeting meeting);
}