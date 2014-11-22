package com.future.gameplatform.account.dao;

import com.future.gameplatform.account.entity.MobileCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface MobileCodeDao {

    MobileCode setMobileCode(String mobile);

    boolean validCode(String mobile, String code);
}
