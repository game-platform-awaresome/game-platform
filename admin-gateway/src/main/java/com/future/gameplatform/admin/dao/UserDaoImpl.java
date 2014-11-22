package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.User;

import java.util.List;

public class UserDaoImpl extends BasicDaoImpl implements UserDao {


    public UserDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public User createUser(User user) {
        datastore.save(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        datastore.save(user);
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        datastore.delete(User.class, userId);
    }

    @Override
    public User findOne(String userId) {
        return datastore.get(User.class, userId);
    }

    @Override
    public List<User> findAll() {
        return datastore.find(User.class).asList();
    }

    @Override
    public User findByUsername(String username) {
        return datastore.find(User.class).filter("username", username).get();
    }
}
