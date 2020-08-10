package com.nchu.service;

import com.nchu.dao.EmployeeDao;
import com.nchu.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Employee)表服务实现类
 *
 * @author makejava
 * @since 2020-08-04 15:59:02
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public Employee doLogin(String name, String password) {
        return employeeDao.doLogin(name,password);
    }

    public boolean insert(Employee employee) {
        employee.setStatus(0);
        employee.setRole(1);
        return employeeDao.insert(employee)>0;
    }

    public List<Employee> getAllByStatus(Integer status) {
        return employeeDao.getAllByStatus(status);
    }

    public boolean update(Integer employeeid, Integer status) {
        return employeeDao.update(employeeid,status)>0;
    }

    public List<Employee> queryAll(Employee employee,Integer page,Integer size) {
        page=(page-1)*size;
        return employeeDao.queryAll(employee,page,size);
    }

    public boolean deleteById(Integer employeeid) {
        return employeeDao.deleteById(employeeid)>0;
    }

    public List<Employee> queryAllByLimit(int offset, int limit) {
        return employeeDao.queryAllByLimit(offset,limit);
    }

    public Long getTotal(Employee employee) {
        return employeeDao.getTotal(employee);
    }

    public List<Employee> queryAllBydepId(Integer depid) {
        return employeeDao.queryAllBydepId(depid);
    }
    /*
    *//**
     * 通过ID查询单条数据
     *
     * @param employeeid 主键
     * @return 实例对象
     *//*
    public Employee queryById(Integer employeeid) {
        return this.employeeDao.queryById(employeeid);
    }

    *//**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     *//*
    public List<Employee> queryAllByLimit(int offset, int limit) {
        return this.employeeDao.queryAllByLimit(offset, limit);
    }

    *//**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 实例对象
     *//*
    public Employee insert(Employee employee) {
        this.employeeDao.insert(employee);
        return employee;
    }

    *//**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 实例对象
     *//*
    public Employee update(Employee employee) {
        this.employeeDao.update(employee);
        return this.queryById(employee.getEmployeeid());
    }

    *//**
     * 通过主键删除数据
     *
     * @param employeeid 主键
     * @return 是否成功
     *//*
    public boolean deleteById(Integer employeeid) {
        return this.employeeDao.deleteById(employeeid) > 0;
    }*/
}