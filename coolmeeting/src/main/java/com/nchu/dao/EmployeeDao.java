package com.nchu.dao;

import com.nchu.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao {
/*

    */
/**
     * 通过ID查询单条数据
     *
     * @param employeeid 主键
     * @return 实例对象
     *//*

    Employee queryById(Integer employeeid);

    */
/**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     *//*

    List<Employee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    */
/**
     * 通过实体作为筛选条件查询
     *
     * @return 对象列表
     *//*

    List<Employee> queryAll();

    */
/**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 影响行数
     *//*

    int insert(Employee employee);

    */
/**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 影响行数
     *//*

    int update(Employee employee);

    */
/**
     * 通过主键删除数据
     *
     * @param employeeid 主键
     * @return 影响行数
     *//*



*/

    Employee doLogin(@Param("username") String username, @Param("password")String password);
    int insert(Employee employee);
    List<Employee> getAllByStatus(Integer status);
    int update(@Param("employeeid") Integer employeeid,@Param("status") Integer status);
    List<Employee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
    List<Employee> queryAll(@Param("emp") Employee employee,@Param("page")Integer page,@Param("size") Integer size);
    Long getTotal(@Param("emp") Employee employee);
    int deleteById(Integer employeeid);
    List<Employee> queryAllBydepId(Integer depid);
}