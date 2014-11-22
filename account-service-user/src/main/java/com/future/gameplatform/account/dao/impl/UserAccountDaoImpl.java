package com.future.gameplatform.account.dao.impl;

import java.util.List;
import java.util.Map;

import com.future.gameplatform.account.UserAccountString;
import com.future.gameplatform.account.dao.UserAccountDao;
import com.future.gameplatform.account.entity.User;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccountDaoImpl extends UserBasicDaoImpl implements UserAccountDao {

    private final Logger logger = LoggerFactory.getLogger(UserAccountDaoImpl.class);

	public UserAccountDaoImpl(String mongoDomain, String dbName) {
		super(mongoDomain);
		datastore = morphia.createDatastore(mongo, dbName);
		datastore.ensureIndexes();
	}

	@Override
	public void deleteById(String id) {
        User account = new User();
		account.setId(Integer.parseInt(id));
		datastore.delete(account);
	}

	@Override
	public User insertAccount(User account) {
		datastore.save(account);
		return account;
	}

	@Override
	public User selectById(String id) {
        User account = new User();
		account.setId(Integer.parseInt(id));
		account = datastore.get(account);
		return account;
	}

	@Override
	public List<User> selectByStatus(String status) {
		Query<User> query = datastore.createQuery(User.class)
		        .filter(UserAccountString.STATUS, status);
		return query.asList();
	}

	@Override
	public void updateStatus(String id, String status) {
		Query<User> query = datastore.createQuery(User.class)
		        .filter(UserAccountString.ID, Integer.parseInt(id));

		UpdateOperations<User> ops = datastore.createUpdateOperations(
                User.class).set(UserAccountString.STATUS, status);
		datastore.findAndModify(query, ops, true, false);
	}

    @Override
    public User updateUser(String id, Map<String, Object> userInfo) {
        logger.debug("update user id:[{}] info:[{}]", id, userInfo);
        Query<User> query = datastore.createQuery(User.class).filter(UserAccountString.ID,Integer.parseInt(id));
        UpdateOperations<User> uo = datastore.createUpdateOperations(User.class);
        if(userInfo.containsKey("passwd")){
            uo.set("passwd",(String)userInfo.get("passwd"));
        }
        if(userInfo.containsKey("mobile")){
            uo.set("mobile",(String)userInfo.get("mobile"));
        }
        if(userInfo.containsKey("nick")){
            uo.set("nick",(String)userInfo.get("nick"));
        }
        if(userInfo.containsKey("sex")){
            uo.set("sex",(String)userInfo.get("sex"));
        }
        User user = datastore.findAndModify(query, uo);
        return user;
    }


    @Override
    public User selectByMobile(String id) {
        return datastore.find(User.class).filter("mobile", id).get();
    }

    @Override
    public User incrementBalance(String uid, long amount) {
        logger.debug("increment balance");
        User user = null;
        try{
            user = datastore.get(User.class,Integer.parseInt(uid));
            logger.debug("get user:[{}]",user);
            long newbalance = user.getBalance() + amount;
            Query<User> userQuery = datastore.createQuery(User.class).filter("id", Integer.parseInt(uid));
            UpdateOperations<User> uo = datastore.createUpdateOperations(User.class).set("balance",newbalance);
            user = datastore.findAndModify(userQuery, uo);
        } catch (Exception e){
            logger.error("update error",e);
            return null;
        }
        return user;
    }
}
