package com.nchu.dao;

import com.nchu.entity.Meetingroom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Meetingroom)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-07 10:21:21
 */
public interface MeetingroomDao {

    /**
     * 通过ID查询单条数据
     *
     * @param roomid 主键
     * @return 实例对象
     */
    Meetingroom queryById(Integer roomid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Meetingroom> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param meetingroom 实例对象
     * @return 对象列表
     */
    List<Meetingroom> queryAll(Meetingroom meetingroom);
    @Select("select * from meetingroom")
    List<Meetingroom> queryAll1();

    /**
     * 新增数据
     *
     * @param meetingroom 实例对象
     * @return 影响行数
     */
    int insert(Meetingroom meetingroom);

    /**
     * 修改数据
     *
     * @param meetingroom 实例对象
     * @return 影响行数
     */
    int update(@Param("room") Meetingroom meetingroom);

    /**
     * 通过主键删除数据
     *
     * @param roomid 主键
     * @return 影响行数
     */
    int deleteById(Integer roomid);

}