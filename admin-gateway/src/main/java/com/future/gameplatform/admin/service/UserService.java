package com.future.gameplatform.admin.service;

import com.future.gameplatform.admin.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    public User createUser(User user);

    public User updateUser(User user);

    public void deleteUser(String userId);

    public void changePassword(String userId, String newPassword);

    public String validAndChangePassword(String username, String oldpwdplain, String pwd);

    User findOne(String userId);

    List<User> findAll();

    public User findByUsername(String username);

    public Set<String> findRoles(String username);


    public Set<String> findPermissions(String username);


    public boolean orgAuth(String shortcode, String key);

}
