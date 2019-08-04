package cn.ztuo.bitrade.service;

import com.querydsl.core.types.Predicate;

import cn.ztuo.bitrade.dao.AdminDao;
import cn.ztuo.bitrade.dao.DepartmentDao;
import cn.ztuo.bitrade.entity.Admin;
import cn.ztuo.bitrade.entity.Department;
import cn.ztuo.bitrade.service.Base.BaseService;
import cn.ztuo.bitrade.util.MessageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author GS
 * @date 2017年12月19日
 */
@Service
public class DepartmentService extends BaseService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private AdminDao adminDao;


    /**
     * 添加或更新部门
     *
     * @param department
     * @return
     */
    public Department save(Department department) {
        return departmentDao.save(department);
    }

    public Department findOne(Long departmentId) {
        return departmentDao.findOne(departmentId);
    }


    public Department getDepartmentDetail(Long departmentId) {
        Department department = departmentDao.findOne(departmentId);
        Assert.notNull(department, "该部门不存在");
        return department;
    }


    public Page<Department> findAll(Predicate predicate, Pageable pageable) {
        return departmentDao.findAll(predicate, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public MessageResult deletes(Long id) {
        Department department = departmentDao.findOne(id);
        List<Admin> list = adminDao.findAllByDepartment(department);
        if (list != null && list.size() > 0) {
            MessageResult result = MessageResult.error("请先删除该部门下的所有用户");
            return result;
        }
        departmentDao.delete(id);
        return MessageResult.success("删除成功");
    }




}
