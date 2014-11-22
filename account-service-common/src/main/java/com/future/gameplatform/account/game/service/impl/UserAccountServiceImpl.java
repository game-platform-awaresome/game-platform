package com.future.gameplatform.account.game.service.impl;


import com.future.gameplatform.account.game.UserAccountString;
import com.future.gameplatform.account.game.dao.AccountingDao;
import com.future.gameplatform.account.game.dao.UserAccountDao;
import com.future.gameplatform.account.game.entity.Accounting;
import com.future.gameplatform.account.game.entity.UserAccount;
import com.future.gameplatform.account.game.service.UserAccountService;
import com.future.gameplatform.common.id.IdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class UserAccountServiceImpl implements UserAccountService {

	private final static Logger logger = LoggerFactory
	        .getLogger(UserAccountServiceImpl.class);

	private UserAccountDao userAccountDao;

    private AccountingDao accountingDao;

	public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

    public void setAccountingDao(AccountingDao accountingDao) {
        this.accountingDao = accountingDao;
    }


    @Override
    public String tradeAccounting(String uid, Map<String, Object> info) {
        logger.debug("trade accounting with uid:[{}] appid:[{}]", uid, info.get("appid"));
        UserAccount userAccount = userAccountDao.selectByUidAppid(uid,(String)info.get("appid"));
        long amount = (Integer)info.get("amount");
        if(userAccount != null){
            logger.debug("get user account");
            long curBalance = userAccount.getBalance();
            long curWithdrew = userAccount.getWithdraw();
            String origtype = (String)info.get("origtype");

            if(UserAccountString.ACCOUNTING_ORIG_TYPE_0.equalsIgnoreCase(origtype)){
                if (amount > curBalance){
                    return UserAccountString.RESULT_FAILED;
                }
            } else {
                amount = 0L - amount;
            }
            userAccount.setBalance(curBalance - amount);
            userAccount.setWithdraw(curWithdrew + amount);
            userAccount = userAccountDao.updateAccounting(userAccount.getId(), userAccount);
        } else {
            logger.error("no user account,so just wrong");
            return UserAccountString.RESULT_FAILED;
        }
        logger.debug("update user balance result: balance:{},withdraw:{}", userAccount.getBalance(),
                userAccount.getWithdraw());
        Accounting accounting = new Accounting();
        accounting.setId(IdFactory.generateId());
        accounting.setUid(uid);
        accounting.setAppid((String)info.get("appid"));
        accounting.setOrigappid((String)info.get("origappid"));
        accounting.setType((String)info.get("type"));
        accounting.setOrigid((String)info.get("origid"));
        accounting.setOrigtype((String)info.get("origtype"));
        accounting.setStatus(UserAccountString.STATUS_OK);
        accounting.setAmount(0-amount);
        accounting.setCreatedDate(new Date());

        accounting = accountingDao.save(accounting);
        logger.debug("save one accounting result id: {}", accounting.getId());
        return UserAccountString.RESULT_OK;
    }

    @Override
    public String rechargeAccounting(String uid, Map<String, Object> info) {
        logger.debug("recharge accounting uid:[{}] info:[{}]", uid, info);
        UserAccount userAccount = userAccountDao.selectByUidAppid(uid, (String)info.get("appid"));
        logger.debug("get user account:[{}]",userAccount);
        String origtype = (String)info.get("origtype");
        long amount = (Integer)info.get("amount");
        long money = (Integer)info.get("money");
        if(userAccount != null){
            logger.debug("update account");
            long curbalance = userAccount.getBalance();
            long curdeposit = userAccount.getDeposit();
            long curmoney = userAccount.getRecharge();
            if(UserAccountString.ACCOUNTING_ORIG_TYPE_1.equalsIgnoreCase(origtype)){
                if (amount > curbalance){
                    return UserAccountString.RESULT_FAILED;
                }
                amount = 0L - amount;
                money = 0L - money;
            }
            userAccount.setBalance(curbalance + amount);
            userAccount.setDeposit(curdeposit + amount);
            userAccount.setRecharge(curmoney  + money);
            userAccount = userAccountDao.updateAccounting(userAccount.getId(), userAccount);
        }else {
            logger.debug("create new user account");
            userAccount = new UserAccount();
            userAccount.setId(IdFactory.generateId());
            userAccount.setUid(uid);
            userAccount.setAppid((String)info.get("appid"));
            userAccount.setCreateDate(new Date());
            userAccount.setStatus(UserAccountString.STATUS_OK);
            if(UserAccountString.ACCOUNTING_ORIG_TYPE_1.equalsIgnoreCase(origtype)){
                if (amount > 0){
                    return UserAccountString.RESULT_FAILED;
                }
                amount = 0L - amount;
                money = 0L - money;
            }
            userAccount.setBalance(amount);
            userAccount.setDeposit(amount);
            userAccount.setRecharge(money);
            userAccount = userAccountDao.insertAccount(userAccount);
        }
        logger.debug("update user balance result: balance:{},deposit:{},recharge:{}", new Object[]{userAccount.getBalance(),
                userAccount.getDeposit(),userAccount.getRecharge()});
        Accounting accounting = new Accounting();
        accounting.setId(IdFactory.generateId());
        accounting.setUid(uid);
        accounting.setAppid((String)info.get("appid"));
        accounting.setPayway((String)info.get("source"));
        accounting.setType((String)info.get("type"));
        accounting.setOrigid((String)info.get("origid"));
        accounting.setOrigtype((String)info.get("origtype"));
        accounting.setStatus(UserAccountString.STATUS_OK);
        accounting.setAmount(amount);
        accounting.setMoney(money);
        accounting.setCreatedDate(new Date());

        accounting = accountingDao.save(accounting);
        logger.debug("save one accounting result id: {}", accounting.getId());
        return UserAccountString.RESULT_OK;
    }

    @Override
    public long accountingbalance(String uid, Map<String, Object> balanceinfo) {
        return 0;
    }

}
