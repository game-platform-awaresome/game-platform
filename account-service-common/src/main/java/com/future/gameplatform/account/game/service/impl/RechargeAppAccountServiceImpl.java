package com.future.gameplatform.account.game.service.impl;

import com.future.gameplatform.account.game.UserAccountString;
import com.future.gameplatform.account.game.dao.RechargeAppAccountDao;
import com.future.gameplatform.account.game.entity.AccountItem;
import com.future.gameplatform.account.game.entity.RechargeAppAccount;
import com.future.gameplatform.account.game.service.RechargeAppAccountService;
import com.future.gameplatform.common.id.IdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class RechargeAppAccountServiceImpl implements RechargeAppAccountService {

    private final Logger logger = LoggerFactory.getLogger(RechargeAppAccountServiceImpl.class);

    private RechargeAppAccountDao rechargeAppAccountDao;

    public void setRechargeAppAccountDao(RechargeAppAccountDao rechargeAppAccountDao) {
        this.rechargeAppAccountDao = rechargeAppAccountDao;
    }

    @Override
    public RechargeAppAccount getById(String appid) {
        try{
            return rechargeAppAccountDao.getById(appid);
        }catch (Exception e){
            logger.error("error get by id", e);
            return null;
        }
    }

    @Override
    public List<RechargeAppAccount> listAll() {
        return rechargeAppAccountDao.listAll();
    }

    @Override
    public String insert(Map<String, Object> accountInfo) {
        logger.debug("insert recharge account");
        RechargeAppAccount rechargeAppAccount = new RechargeAppAccount();
        rechargeAppAccount.setId(IdFactory.generateId());
        rechargeAppAccount.setAppkey(IdFactory.generateId());
        if(accountInfo.containsKey("payway"))
            rechargeAppAccount.setPayway((String)accountInfo.get("payway"));
        if(accountInfo.containsKey("searchurl"))
            rechargeAppAccount.setSearchurl((String)accountInfo.get("searchurl"));
        if(accountInfo.containsKey("noticeurl"))
            rechargeAppAccount.setNoticeurl((String)accountInfo.get("noticeurl"));
        rechargeAppAccount.setShortcode((String)accountInfo.get("shortcode"));
        rechargeAppAccount.setCpName((String)accountInfo.get("cpName"));
        rechargeAppAccount.setStatus(UserAccountString.STATUS_OK);
        if(accountInfo.containsKey("payextra")){
            Map<String,Object> extra = (Map<String,Object>)accountInfo.get("payextra");
            rechargeAppAccount.setPayextra(extra);
        }
        RechargeAppAccount defaultTemplate = rechargeAppAccountDao.getById(UserAccountString.RECHARGE_ACCOUNT_DEFAULT_ID);
        List<AccountItem> items = defaultTemplate.getItems();

        rechargeAppAccount.setItems(items);
        rechargeAppAccountDao.insert(rechargeAppAccount);
        return rechargeAppAccount.getId();
    }

    @Override
    public String deleteById(String appid) {
        rechargeAppAccountDao.deleteById(appid);
        return UserAccountString.RESULT_OK;
    }

    @Override
    public String updateRechargeApp(String appid, Map<String, Object> updateInfo) {
        RechargeAppAccount account = rechargeAppAccountDao.getById(appid);
        if(updateInfo.containsKey("status")){
            account.setStatus(updateInfo.get("status").toString());
        }
        if(updateInfo.containsKey("noticeurl")){
            account.setNoticeurl(updateInfo.get("noticeurl").toString());
        }
        if(updateInfo.containsKey("cpName")){
            account.setCpName(updateInfo.get("cpName").toString());
        }
        if(updateInfo.containsKey("searchurl")){
            account.setSearchurl(updateInfo.get("searchurl").toString());
        }
        return rechargeAppAccountDao.insert(account).getId();
    }

    @Override
    public String updateAccountChannel(String id, String channel, AccountItem item) {
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountDao.getById(id);
        List<AccountItem> itemList = rechargeAppAccount.getItems();
        Iterator<AccountItem> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            AccountItem itemEntry = itemIterator.next();
            if(channel.equals(itemEntry.getChannel())){
                if(StringUtils.hasText(item.getStatus())){
                    logger.debug("update status channel[{}]", channel);
                    itemEntry.setStatus(item.getStatus());
                }
                if(StringUtils.hasText(item.getVersion())){
                    logger.debug("update version channel[{}]", channel);
                    itemEntry.setVersion(item.getVersion());
                }
                if(item.getSortcode()>0){
                    logger.debug("update sortcode channel[{}]", channel);
                    itemEntry.setSortcode(item.getSortcode());
                }
                if(StringUtils.hasText(item.getFee_min())){
                    itemEntry.setFee_min(item.getFee_min());
                }
                if(StringUtils.hasText(item.getFee_max())){
                    itemEntry.setFee_max(item.getFee_max());
                }
            }
        }
        rechargeAppAccountDao.insert(rechargeAppAccount);
        return UserAccountString.RESULT_OK;
    }

    @Override
    public String updateOrInsertDefaultAccountChannel(AccountItem item) {
        List<RechargeAppAccount> accountList = rechargeAppAccountDao.listAll();
        Iterator<RechargeAppAccount> accountIterator = accountList.iterator();
        while(accountIterator.hasNext()){
            RechargeAppAccount account = accountIterator.next();
            /*
            if(account.getId().equals(UserAccountString.RECHARGE_ACCOUNT_DEFAULT_ID) || !account.getStatus().equalsIgnoreCase(UserAccountString.STATUS_OK)){
                continue;
            }
            */
            List<AccountItem> itemList = account.getItems();
            Iterator<AccountItem> itemIterator = itemList.iterator();
            boolean newItem = true;
            while (itemIterator.hasNext()){
                AccountItem accountItem = itemIterator.next();
                if(accountItem.getChannel().equals(item.getChannel())){
                    newItem = false;
                    if(StringUtils.hasText(item.getVersion())){
                        accountItem.setVersion(item.getVersion());
                    }
                    if(StringUtils.hasText(item.getStatus())){
                        accountItem.setStatus(item.getStatus());
                    }
                    if(item.getSortcode()>0){
                        accountItem.setSortcode(item.getSortcode());
                    }
                    if(StringUtils.hasText(item.getFee_min())){
                        accountItem.setFee_min(item.getFee_min());
                    }
                    if(StringUtils.hasText(item.getFee_max())){
                        accountItem.setFee_max(item.getFee_max());
                    }
                }
            }
            if(newItem){
                itemList.add(item);
            }
            rechargeAppAccountDao.insert(account);
        }
        return UserAccountString.RESULT_OK;
    }


    @Override
    public RechargeAppAccount getByCode(String shortcode, String operator, String fee, String version) {
        RechargeAppAccount account  = rechargeAppAccountDao.getByShortcode(shortcode);
        AccountItem targetItem = null;
        AccountItem alipayItem = null;
        List<AccountItem> items = account.getItems();
        Iterator<AccountItem> iterator = items.iterator();
        BigDecimal feed = new BigDecimal(fee);
        while(iterator.hasNext()){
            AccountItem pageEntry = iterator.next();
            BigDecimal fee_mind = new BigDecimal(pageEntry.getFee_min());
            BigDecimal fee_maxd = new BigDecimal(pageEntry.getFee_max());
            if(pageEntry.getOperator().equalsIgnoreCase("alipay")) {
                alipayItem = pageEntry;
                iterator.remove();
                continue;
            }
            if(!pageEntry.getStatus().equalsIgnoreCase("ok")){
                iterator.remove();
                continue;
            }
            if(operator != null && !pageEntry.getOperator().equalsIgnoreCase(operator)){
                iterator.remove();
                continue;
            } else if(feed.compareTo(fee_mind) < 0 || feed.compareTo(fee_maxd) > 0){
                iterator.remove();
                continue;
            }  else if(pageEntry.getVersion() != null){
                if(pageEntry.getVersion().indexOf(version)<0){
                    iterator.remove();
                    continue;
                }
            }
            if(targetItem ==  null){
                targetItem = pageEntry;
                iterator.remove();
                continue;
            } else {
                BigDecimal targetFee = new BigDecimal(targetItem.getFee());
                BigDecimal feeItem = new BigDecimal(pageEntry.getFee());
                if(feed.equals(targetFee) && feed.equals(feeItem) && targetItem.getSortcode() < pageEntry.getSortcode()){
                    targetItem = pageEntry;
                } else if (!feed.equals(targetFee)){
                    if(feeItem.equals(feed)){
                        targetItem = pageEntry;
                    } else if(targetFee.compareTo(feeItem) < 0){
                        targetItem = pageEntry;
                    } else if(targetFee.equals(feeItem) && targetItem.getSortcode() < pageEntry.getSortcode()) {
                        targetItem = pageEntry;
                    }
                }
                iterator.remove();
                continue;
            }
        }
        if(targetItem != null){
            items.add(targetItem);
        } else {
            items.add(alipayItem);
        }

        account.setAppkey(null);
        account.setCpName(null);
        account.setNoticeurl(null);
        account.setSearchurl(null);
        if(account.getItems().size() < 1){
            account.setStatus("deleted");
        }
        return account;
    }

    @Override
    public String getKeyByCode(String shortcode) {
        RechargeAppAccount account  = rechargeAppAccountDao.getByShortcode(shortcode);
        if(account == null)
            return null;
        return account.getAppkey();
    }

    @Override
    public List<RechargeAppAccount> listAllSimple() {
        List<RechargeAppAccount> accountList = rechargeAppAccountDao.listAllSimple();
        return accountList;
    }

    @Override
    public RechargeAppAccount save(RechargeAppAccount rechargeAppAccount) {
        rechargeAppAccountDao.insert(rechargeAppAccount);
        return rechargeAppAccount;
    }
}
