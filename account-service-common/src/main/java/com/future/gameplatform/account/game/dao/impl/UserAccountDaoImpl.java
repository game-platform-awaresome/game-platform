package com.future.gameplatform.account.game.dao.impl;

import com.future.gameplatform.account.game.UserAccountString;
import com.future.gameplatform.account.game.dao.UserAccountDao;
import com.future.gameplatform.account.game.entity.UserAccount;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserAccountDaoImpl extends BasicDaoImpl implements UserAccountDao {


	public UserAccountDaoImpl(String mongoDomain, String dbName) {
		super(mongoDomain);
		datastore = morphia.createDatastore(mongo, dbName);
		datastore.ensureIndexes();
	}

	@Override
	public void deleteById(String id) {
        UserAccount account = new UserAccount();
		account.setId(id);
		datastore.delete(account);
	}

	@Override
	public UserAccount insertAccount(UserAccount account) {
		datastore.save(account);
		return account;
	}

	@Override
	public UserAccount selectById(String id) {
        UserAccount account = new UserAccount();
		account.setId(id);
		account = datastore.get(account);
		return account;
	}

    @Override
    public UserAccount selectByUidAppid(String uid, String appid) {
        try{
             return datastore.find(UserAccount.class).filter("uid",uid).filter("appid", appid).get();
        }catch (Exception e){
            logger.error("error find",e);
            return null;
        }
    }

    @Override
	public List<UserAccount> selectByStatus(String status) {
		Query<UserAccount> query = datastore.createQuery(UserAccount.class)
		        .filter(UserAccountString.STATUS, status);
		return query.asList();
	}

	@Override
	public void updateStatus(String id, String status) {
		Query<UserAccount> query = datastore.createQuery(UserAccount.class)
		        .filter(UserAccountString.ID, id);

		UpdateOperations<UserAccount> ops = datastore.createUpdateOperations(
                UserAccount.class).set(UserAccountString.STATUS, status);
		datastore.findAndModify(query, ops, true, false);
	}

    @Override
    public UserAccount updateAccounting(String uid, UserAccount userAccount) {
        logger.debug("update user account with id:[{}] info:[{}]",uid,userAccount);
        try{
        Query<UserAccount> query = datastore.createQuery(UserAccount.class).filter(UserAccountString.ID, uid);
        UpdateOperations<UserAccount> uo = datastore.createUpdateOperations(UserAccount.class)
                .set("balance", userAccount.getBalance()).set("withdraw", userAccount.getWithdraw())
                .set("deposit", userAccount.getDeposit()).set("recharge", userAccount.getRecharge());
        datastore.findAndModify(query, uo);
        }catch (Exception e){
            logger.error("something error",e);
        }
        return userAccount;
    }

    @Override
    public UserAccount selectByMobile(String id) {
        return datastore.find(UserAccount.class).filter("mobile", id).get();
    }
}
