package com.nchu.service;

import com.nchu.dao.DepartmentDao;
import com.nchu.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Department)表服务实现类
 *
 * @author makejava
 * @since 2020-08-04 19:59:33
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param departmentid 主键
     * @return 实例对象
     */
    public Department queryById(Integer departmentid) {
        return this.departmentDao.queryById(departmentid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<Department> queryAllByLimit(int offset, int limit) {
        return this.departmentDao.queryAllByLimit(offset, limit);
    }
    public List<Department> queryAll() {
        return this.departmentDao.queryAll();
    }
    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    public boolean insert(Department department) {
        return this.departmentDao.insert(department)>0;
    }

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    public Department update(Department department) {
        this.departmentDao.update(department);
        return this.queryById(department.getDepartmentid());
    }

    public boolean updatedep(Integer id, String name) {
        return departmentDao.updatedep(id,name)>0;
    }

    /**
     * 通过主键删除数据
     *
     * @param departmentid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer departmentid) {
        return this.departmentDao.deleteById(departmentid) > 0;
    }
}