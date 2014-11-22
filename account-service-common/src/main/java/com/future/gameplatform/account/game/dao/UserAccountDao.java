package com.future.gameplatform.account.game.dao;

import com.future.gameplatform.account.game.entity.UserAccount;

import java.util.List;


public interface UserAccountDao {
	void deleteById(String id);

    UserAccount insertAccount(UserAccount account);

    UserAccount selectById(String id);

    UserAccount selectByUidAppid(String uid, String appid);

	List<UserAccount> selectByStatus(String status);

	void updateStatus(String id, String status);

    UserAccount updateAccounting(String uid, UserAccount userAccount);

    UserAccount selectByMobile(String id);
}
