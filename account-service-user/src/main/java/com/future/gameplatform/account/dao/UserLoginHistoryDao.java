package com.future.gameplatform.account.dao;

import com.future.gameplatform.account.entity.UserLoginHistory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface UserLoginHistoryDao {

    String insert(UserLoginHistory userLoginHistory);

    long countByChannel(String cid);

    long countByUser(String uid);
}
