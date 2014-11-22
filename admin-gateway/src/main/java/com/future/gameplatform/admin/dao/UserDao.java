package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.User;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserDao {

    public User createUser(User user);

    public User updateUser(User user);

    public void deleteUser(String userId);

    User findOne(String userId);

    List<User> findAll();

    User findByUsername(String username);

}
