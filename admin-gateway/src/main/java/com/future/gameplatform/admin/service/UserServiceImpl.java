package com.future.gameplatform.admin.service;

import com.future.gameplatform.admin.dao.UserDao;
import com.future.gameplatform.admin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RechargeHelper rechargeHelper;

    /**
     * 创建用户
     * @param user
     */
    public User createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        userDao.deleteUserByOrg(user.getOrganizationId());
        return userDao.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(String userId, String newPassword) {
        User user =userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }


    public String validAndChangePassword(String username, String oldpwdplain, String pwd){
        User user = userDao.findByUsername(username);
        if(passwordHelper.pwdValid(user, oldpwdplain)){
            user.setPassword(pwd);
            passwordHelper.encryptPassword(user);
            userDao.updateUser(user);
            return "success";
        } else {
            return "旧密码不对！";
        }
    }

    @Override
    public User findOne(String userId) {
        return userDao.findOne(userId);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        User user =findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return new HashSet<String>(user.getRoleIds());
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        User user =findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findPermissions(user.getRoleIds().toArray(new String[0]));
    }

    @Override
    public boolean orgAuth(String shortcode, String key) {
        return rechargeHelper.doOrgAuth(shortcode, key);
    }

}
