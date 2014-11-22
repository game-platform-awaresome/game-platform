package com.future.gameplatform.account.dao.impl;

import com.future.gameplatform.account.dao.MobileCodeDao;
import com.future.gameplatform.account.entity.MobileCode;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class MobileCodeDaoImpl extends UserBasicDaoImpl implements MobileCodeDao {

    private static final Logger logger = LoggerFactory.getLogger(MobileCodeDaoImpl.class);

    public MobileCodeDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public MobileCode setMobileCode(String mobile) {
        int codeInt = Math.abs((new Random()).nextInt())%1000000;
        if(codeInt < 100000){
            codeInt = codeInt + 100000;
        }
        String code = String.valueOf(codeInt);
        logger.debug("code:[{}]",code);
        MobileCode mobileCode = datastore.get(MobileCode.class, mobile);
        if(mobileCode ==  null){
            logger.debug("new mobile:[{}]",mobile);
            mobileCode = new MobileCode();
            mobileCode.setId(mobile);
            mobileCode.setCode(code);
            mobileCode.setCreatedDate(new Date());
            datastore.save(mobileCode);
        }else {
            Query<MobileCode> query = datastore.createQuery(MobileCode.class).filter("id", mobile);
            UpdateOperations<MobileCode> mobileCodeUpdateOperations = datastore.createUpdateOperations(MobileCode.class).set("code", code).set("createdDate", new Date());
            mobileCode = datastore.findAndModify(query, mobileCodeUpdateOperations);
            logger.debug("updated mobilecode:[{}]",mobileCode);
        }

        return mobileCode;
    }

    @Override
    public boolean validCode(String mobile, String code) {
        MobileCode mobileCode = datastore.get(MobileCode.class, mobile);
        if(mobileCode != null && code.equalsIgnoreCase(mobileCode.getCode())){
            long curDt = new Date().getTime();
            long validDt = mobileCode.getCreatedDate().getTime();
            if((curDt - validDt) < 30 * 60 *1000){
                return true;
            }
            logger.error("valid code out of time limit 30m");
        }
        logger.error("not an valid mobile or code ");
        return false;
    }
}
