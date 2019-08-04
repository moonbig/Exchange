
package cn.ztuo.bitrade.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sparkframework.lang.Convert;
import com.sparkframework.security.Encrypt;

import cn.ztuo.bitrade.annotation.AccessLog;
import cn.ztuo.bitrade.constant.AdminModule;
import cn.ztuo.bitrade.constant.PageModel;
import cn.ztuo.bitrade.constant.SysConstant;
import cn.ztuo.bitrade.controller.common.BaseAdminController;
import cn.ztuo.bitrade.core.Menu;
import cn.ztuo.bitrade.entity.Admin;
import cn.ztuo.bitrade.entity.Department;
import cn.ztuo.bitrade.entity.QAdmin;
import cn.ztuo.bitrade.entity.SysRole;
import cn.ztuo.bitrade.service.AdminService;
import cn.ztuo.bitrade.service.DepartmentService;
import cn.ztuo.bitrade.service.SysPermissionService;
import cn.ztuo.bitrade.service.SysRoleService;
import cn.ztuo.bitrade.util.BindingResultUtil;
import cn.ztuo.bitrade.util.CaptchaUtil;
import cn.ztuo.bitrade.util.MessageResult;
import cn.ztuo.bitrade.vendor.provider.SMSProvider;
import lombok.extern.slf4j.Slf4j;




/**
 * @author GS
 * @date 2017年12月19日
 */


@Slf4j
@Controller
@RequestMapping("/system/employee")
public class EmployeeController extends BaseAdminController {

    @Value("${spark.system.md5.key}")
    private String md5Key;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private DepartmentService departmentService;
    @Resource
    private SysPermissionService sysPermissionService;

    @Autowired
    private RedisTemplate redisTemplate ;

    @Autowired
    private SMSProvider smsProvider ;



/**
     * 提交登录信息
     *
     * @param request
     * @return
     */


    @RequestMapping(value = "sign/in")
    @ResponseBody
    @AccessLog(module = AdminModule.SYSTEM, operation = "提交登录信息Admin")
    public MessageResult doLogin(@SessionAttribute("username")String username,
                                 @SessionAttribute("password")String password,
                                 @SessionAttribute("phone")String phone,String code,
                                 @RequestParam(value="rememberMe",defaultValue = "true")boolean rememberMe,
                                 HttpServletRequest request) {
        Assert.notNull(code,"请输入验证码");
        Assert.isTrue(StringUtils.isNotEmpty(username)&&StringUtils.isNotEmpty(password)&&StringUtils.isNotEmpty(phone),"会话已过期");
        ValueOperations valueOperations = redisTemplate.opsForValue() ;
        Object cacheCode = valueOperations.get(SysConstant.ADMIN_LOGIN_PHONE_PREFIX+phone);
        Assert.notNull(cacheCode,"验证码已经被清除，请重新发送");
        if (!code.equals(cacheCode.toString())) {
            return error("手机验证码错误，请重新输入");
        }
        try {
            log.info("md5Key {}", md5Key);

            //password = Encrypt.MD5(password + md5Key);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password,true);
            token.setHost(getRemoteIp(request));
            SecurityUtils.getSubject().login(token);
            valueOperations.getOperations().delete(SysConstant.ADMIN_LOGIN_PHONE_PREFIX+phone);
            Admin admin = (Admin) request.getSession().getAttribute(SysConstant.SESSION_ADMIN);
            //token.setRememberMe(true);

            //获取当前用户的菜单权限
            List<Menu> list;
            if ("root".equalsIgnoreCase(admin.getUsername())) {
                list = sysRoleService.toMenus(sysPermissionService.findAll(), 0L);
            } else {
                list = sysRoleService.toMenus(sysRoleService.getPermissions(admin.getRoleId()), 0L);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("permissions", list);
            map.put("admin", admin);
            return success("登录成功", map);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }


    @RequestMapping(value = "/check")
    @ResponseBody
    @AccessLog(module = AdminModule.SYSTEM, operation = "判断后台登录输入手机验证码")
    public MessageResult valiatePhoneCode(HttpServletRequest request){
        String username = Convert.strToStr(request(request, "username"), "");
        String password = Convert.strToStr(request(request, "password"), "");
        String captcha = Convert.strToStr(request(request, "captcha"), "");
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return error("用户名或密码不能为空");
        }
        HttpSession session = request.getSession();
        if (StringUtils.isBlank(captcha)) {
            return error("验证码不能为空");
        }
        String ADMIN_LOGIN = "ADMIN_LOGIN";
        if (!CaptchaUtil.validate(session, ADMIN_LOGIN, captcha)) {
            return error("验证码不正确");
        }
        password = Encrypt.MD5(password + md5Key);
        Admin admin = adminService.login(username,password);
        if(admin==null){
            return error("用户名或密码不存在");
        }else{
            try {
                request.getSession().setAttribute("username",username);
                request.getSession().setAttribute("password",password);
                request.getSession().setAttribute("phone",admin.getMobilePhone());

               return success("",admin.getMobilePhone());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return error("手机验证码发送或保存失败");
        }
    }






/**
     * 退出登录
     *
     * @return
     */


    @RequestMapping(value = "logout")
    @ResponseBody
    @AccessLog(module = AdminModule.SYSTEM, operation = "退出登录")
    public MessageResult logout() {
        SecurityUtils.getSubject().logout();
        return success();
    }



/**
     * 创建或更改后台用户
     *
     * @param admin
     * @param bindingResult
     * @return
     */


    @RequiresPermissions("system:employee:merge")
    @RequestMapping(value = "/merge")
    @ResponseBody
    @AccessLog(module = AdminModule.SYSTEM, operation = "创建或更改后台用户")
    @Transactional(rollbackFor = Exception.class)
    public MessageResult addAdmin(Admin admin,
                                  @RequestParam("departmentId") Long departmentId,
                                  BindingResult bindingResult) {
        MessageResult result = BindingResultUtil.validate(bindingResult);
        if (result != null) {
            return result;
        }
        Assert.notNull(departmentId, "请选择部门");
        Department department = departmentService.findOne(departmentId);
        admin.setDepartment(department);
        String password;
        if (admin.getId() != null) {
            Admin admin1 = adminService.findOne(admin.getId());
            admin.setLastLoginIp(admin1.getLastLoginIp());
            admin.setLastLoginTime(admin1.getLastLoginTime());
            //如果密码不为null更改密码
            if (StringUtils.isNotBlank(admin.getPassword())) {
                password = Encrypt.MD5(admin.getPassword() + md5Key);
            } else {
                password = admin1.getPassword();
            }
        } else {
            //这里是新增
            Admin a = adminService.findByUsername(admin.getUsername());
            if (a != null) {
                return error("用户名已存在！");
            }
            if (StringUtils.isBlank(admin.getPassword())) {
                return error("密码不能为空");
            }
            password = Encrypt.MD5(admin.getPassword() + md5Key);
        }
        admin.setPassword(password);
        adminService.saveAdmin(admin);
        return success("操作成功");
    }

    @ResponseBody
    @RequiresPermissions("system:employee:page-query")
    @PostMapping("page-query")
    @AccessLog(module = AdminModule.SYSTEM, operation = "分页查找后台用户admin")
    public MessageResult findAllAdminUser(
            PageModel pageModel,
            @RequestParam(value = "searchKey", defaultValue = "") String searchKey) {
        BooleanExpression predicate = QAdmin.admin.username.ne("root");
        if (StringUtils.isNotBlank(searchKey)) {
            predicate.and(QAdmin.admin.email.like(searchKey)
                    .or(QAdmin.admin.realName.like(searchKey))
                    .or(QAdmin.admin.mobilePhone.like(searchKey))
                    .or(QAdmin.admin.username.like(searchKey)));
        }
        Page<Admin> all = adminService.findAll(predicate, pageModel.getPageable());
        for (Admin admin : all.getContent()) {
            SysRole role = sysRoleService.findOne(admin.getRoleId());
            admin.setRoleName(role.getRole());
        }
        return success(all);
    }

    @RequiresPermissions("system:employee:update-password")
    @PostMapping("update-password")
    @ResponseBody
    public MessageResult updatePassword(Long id, String lastPassword, String newPassword) {
        Assert.notNull(id, "admin id 不能为null");
        Assert.notNull(lastPassword, "请输入原密码");
        Assert.notNull(newPassword, "请输入新密码");
        Admin admin = adminService.findOne(id);
        lastPassword = Encrypt.MD5(lastPassword + md5Key);
        Assert.isTrue(lastPassword.equalsIgnoreCase(admin.getPassword()), "密码错误");
        admin.setPassword(Encrypt.MD5(newPassword + md5Key));
        adminService.save(admin);
        return MessageResult.success("修改密码成功");
    }


    @PostMapping("reset-password")
    @ResponseBody
    public MessageResult resetPassword(Long id) {
        Assert.notNull(id, "admin id 不能为null");
        Admin admin = adminService.findOne(id);
        admin.setPassword(Encrypt.MD5("123456" + md5Key));
        adminService.save(admin);
        return MessageResult.success("重置密码成功，默认密码123456");
    }



/**
     * admin信息
     *
     * @param id
     * @return
     */


    @RequiresPermissions("system:employee:detail")
    @RequestMapping(value = "/detail")
    @ResponseBody
    @AccessLog(module = AdminModule.SYSTEM, operation = "后台用户Admin详情")
    public MessageResult adminDetail(Long id) {
        Map map = adminService.findAdminDetail(id);
        MessageResult result = success();
        result.setData(map);
        return result;
    }



/**
     * admin信息
     *
     * @return
     */


    @RequiresPermissions("system:employee:deletes")
    @RequestMapping(value = "/deletes")
    @ResponseBody
    @AccessLog(module = AdminModule.SYSTEM, operation = "后台用户Admin详情")
    public MessageResult deletes(Long[] ids) {
        adminService.deletes(ids);
        return MessageResult.success("批量删除成功");
    }
}

