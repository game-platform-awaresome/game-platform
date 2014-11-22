package com.future.gameplatform.account.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;


import com.future.gameplatform.account.UserAccountString;
import com.future.gameplatform.account.dao.MobileCodeDao;
import com.future.gameplatform.account.dao.UserAccountDao;
import com.future.gameplatform.account.dao.UserIdsDao;
import com.future.gameplatform.account.dao.UserLoginHistoryDao;
import com.future.gameplatform.account.entity.MobileCode;
import com.future.gameplatform.account.entity.User;

import com.future.gameplatform.account.entity.UserLoginHistory;
import com.future.gameplatform.account.service.UserAccountService;
import com.future.gameplatform.common.id.IdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccountServiceImpl implements UserAccountService {

	private final static Logger logger = LoggerFactory
	        .getLogger(UserAccountServiceImpl.class);

	private UserAccountDao userAccountDao;

    private UserIdsDao userIdsDao;

    private SendValidCodeHelper sendValidCodeHelper;

    private MobileCodeDao mobileCodeDao;

    private AccountingBalanceHelper accountingBalanceHelper;

    private UserLoginHistoryDao userLoginHistoryDao;

    public void setUserLoginHistoryDao(UserLoginHistoryDao userLoginHistoryDao) {
        this.userLoginHistoryDao = userLoginHistoryDao;
    }

    public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

    public void setUserIdsDao(UserIdsDao userIdsDao) {
        this.userIdsDao = userIdsDao;
    }

    public void setSendValidCodeHelper(SendValidCodeHelper sendValidCodeHelper) {
        this.sendValidCodeHelper = sendValidCodeHelper;
    }

    public void setMobileCodeDao(MobileCodeDao mobileCodeDao) {
        this.mobileCodeDao = mobileCodeDao;
    }

    public void setAccountingBalanceHelper(AccountingBalanceHelper accountingBalanceHelper) {
        this.accountingBalanceHelper = accountingBalanceHelper;
    }

    private String composeReturnJson(String status,String token) {
        if(token == null)
		    return new StringBuilder("{\"status\":\"").append(status).append("\"}").toString();
        else
            return new StringBuilder("{\"status\":\"").append(status).append("\",\"token\":\"" + token + "\"}").toString();
	}

	@Override
	public String auth(String id, String key, String channelid) {
        User account = null;
        if(id.length() < 9){
            account = userAccountDao.selectById(id);
        }else {
            account = userAccountDao.selectByMobile(id);
        }
		if (account != null && (account.getPasswd() != null
		        && account.getPasswd().equals(key))) {
			if (UserAccountString.STATUS_NORMAL.equals(account.getStatus())) {
				logger.debug("Auth success, id:{},key:{}", account.getId(),
				        account.getPasswd());

                UserLoginHistory userLoginHistory = new UserLoginHistory();
                userLoginHistory.setId(IdFactory.generateId());
                if(channelid == null || channelid.isEmpty()) {
                    channelid = "000000";
                }
                userLoginHistory.setChannelid(channelid);
                userLoginHistory.setUid(id);
                userLoginHistory.setLogintime(new Date());

                userLoginHistoryDao.insert(userLoginHistory);
				return composeReturnJson(UserAccountString.STATUS_OK,account.getToken());
			} else {
				logger.warn(
				        "Auth failed,reason for 'FREEZED' status, Id:{},key:{}",
				        account.getId(),
				        account.getPasswd());
				return composeReturnJson(UserAccountString.STATUS_FREEZED,null);
			}
		}
		logger.warn("Auth failed, Id:{},key:{}", id, key);
		return composeReturnJson(UserAccountString.STATUS_FAILED,null);
	}

	@Override
	public User getAccountById(String id) {
		final User result = userAccountDao.selectById(id);
		return result;
	}

    @Override
    public User updateUser(String id, Map<String, Object> userInfo) {
        return userAccountDao.updateUser(id,userInfo);
    }

    @Override
	public String register(Map<String, Object> info) {
        logger.debug("service user register");
        User account = new User();

		account.setStatus(UserAccountString.STATUS_NORMAL);
		account.setId(this.userIdsDao.getNextIdValue());
		account.setPasswd("123456");
        account.setNick(String.valueOf(account.getId()));
		account.setCreateDate(new Date());
		account.setExtra(info);

        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setId(IdFactory.generateId());
        userLoginHistory.setUid(String.valueOf(account.getId()));
        userLoginHistory.setChannelid((String)info.get("channelid"));
        userLoginHistory.setLogintime(new Date());
        userLoginHistoryDao.insert(userLoginHistory);

		account = userAccountDao.insertAccount(account);

		logger.debug("Register result: id:{},key:{}", account.getId(),
		        account.getPasswd());
		return getRegInfo(account);
	}

    private String getRegInfo(User account) {
        return new StringBuilder("{\"id\":").append(account.getId()).append(",\"passwd\":\""+account.getPasswd()+"\",\"token\":\"" + account.getToken() + "\"}").toString();
    }

    @Override
    public String getValidCode(String mobile) {
        MobileCode mobileCode = this.mobileCodeDao.setMobileCode(mobile);
        if(mobileCode != null){
            try {
                sendValidCodeHelper.sendValidCode(mobile, mobileCode.getCode());
            } catch (UnsupportedEncodingException e) {
                logger.error("send code by sms error,msg[{}]",e);
            }
        }
        return UserAccountString.RESULT_OK;
    }

    @Override
    public String getbackUser(Map<String, Object> mobileInfo) {
        if(this.mobileCodeDao.validCode((String)mobileInfo.get("mobile"), (String)mobileInfo.get("code"))){
            User userAccount = this.userAccountDao.selectByMobile((String)mobileInfo.get("mobile"));
            if(userAccount != null){
                return "{\"id\":\""+userAccount.getId()+"\",\"passwd\":\""+userAccount.getPasswd()+"\",\"token\":\""+userAccount.getToken()+"\"}";
            }
        }
        return UserAccountString.RESULT_FAILED;
    }

    @Override
    public String bindMobile(String uid, Map<String, Object> bindInfo) {
        if(this.mobileCodeDao.validCode((String)bindInfo.get("mobile"), (String)bindInfo.get("code"))){
            this.userAccountDao.updateUser(uid, bindInfo);
            return UserAccountString.RESULT_OK;
        }
        return UserAccountString.RESULT_FAILED;
    }

    @Override
    public String doTradeNotice(String uid, Map<String, Object> tradeinfo) {
        String origtype = (String)tradeinfo.get("origtype");
        long amount = (Integer)tradeinfo.get("amount");
        if("0".equalsIgnoreCase(origtype)){
            amount = 0 - amount;
        }
        User user = userAccountDao.incrementBalance(uid, amount);
        return UserAccountString.RESULT_OK;
    }

    @Override
    public String doRechargeNotice(String uid, Map<String, Object> rechargeinfo) {
        logger.debug("do recharge notice uid:[{}] info:[{}]",uid, rechargeinfo);
        String origtype = (String)rechargeinfo.get("origtype");
        long amount = (Integer)rechargeinfo.get("amount");
        logger.debug("parsed value origtype:[{}] amount:[{}]", origtype, amount);
        if("1".equalsIgnoreCase(origtype)){
            amount = 0 - amount;
        }
        User user = userAccountDao.incrementBalance(uid, amount);
        return UserAccountString.RESULT_OK;
    }

    @Override
    public User accountingbalance(String uid,String token) {
        User user = userAccountDao.selectById(uid);
        long result = accountingBalanceHelper.doAccountingBalance(uid, token, user.getBalance());
        if(result != 0){
            logger.debug("do user accounting balance with uid:[{}],get balance diff:[{}]", uid, result);
            user = userAccountDao.incrementBalance(uid,result);
        }
        return user;
    }
}
