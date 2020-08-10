package com.nchu.service;

import com.nchu.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Employee)表服务接口
 *
 * @author makejava
 * @since 2020-08-04 15:41:07
 */
public interface EmployeeService {
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
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     *//*

    List<Employee> queryAllByLimit(int offset, int limit);

    */
/**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 实例对象
     *//*

    Employee insert(Employee employee);

    */
/**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 实例对象
     *//*

    Employee update(Employee employee);

    */
/**
     * 通过主键删除数据
     *
     * @param employeeid 主键
     * @return 是否成功
     *//*

    boolean deleteById(Integer employeeid);
*/

    Employee doLogin(String name, String password);
    boolean insert(Employee employee);
    List<Employee> getAllByStatus(Integer status);
    boolean update(@Param("employeeid") Integer employeeid, @Param("status") Integer status);
    List<Employee> queryAll(Employee employee,@Param("page")Integer page,@Param("size") Integer size);
    boolean deleteById(Integer employeeid);
    List<Employee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
    Long getTotal(@Param("emp") Employee employee);
    List<Employee> queryAllBydepId(Integer depid);
}