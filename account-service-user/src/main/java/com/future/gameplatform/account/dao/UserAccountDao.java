package com.future.gameplatform.account.dao;

import com.future.gameplatform.account.entity.User;

import java.util.List;
import java.util.Map;


public interface UserAccountDao {
	void deleteById(String id);

    User insertAccount(User account);

    User selectById(String id);

	List<User> selectByStatus(String status);

	void updateStatus(String id, String status);

    User updateUser(String id, Map<String,Object> userInfo);

    User selectByMobile(String id);

    User incrementBalance(String uid, long amount);
}
