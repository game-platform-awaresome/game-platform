package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.trade.dao.NoticeCPRecordDao;
import com.future.gameplatform.trade.entity.NoticeCPRecord;
import com.future.gameplatform.trade.service.NoticeCPTaskService;
import com.future.gameplatform.trade.util.TradeString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class NoticeCPTask implements Runnable, Delayed {

    private final static Logger logger = LoggerFactory.getLogger(NoticeCPTask.class);

    private String taskid;
    private long createAt;
    private long executeAt;
    private int delayTimeInMinute;

    private NoticeCPRecordDao noticeCPRecordDao;
    private RechargeNoticeCPHelper rechargeNoticeCPHelper;


    public NoticeCPTask(String taskid, int delayTimeInMinute, NoticeCPRecordDao noticeCPRecordDao, RechargeNoticeCPHelper rechargeNoticeCPHelper) {
        this.delayTimeInMinute=delayTimeInMinute;
        this.taskid = taskid;
        this.noticeCPRecordDao = noticeCPRecordDao;
        this.rechargeNoticeCPHelper = rechargeNoticeCPHelper;
        createAt = new Date().getTime();
        executeAt = createAt + delayTimeInMinute * 60*1000;
    }

    public String getTaskid() {
        return taskid;
    }

    public int getDelayValue(){
        return delayTimeInMinute;
    }

    public void setExecuteAt(long delay)
    {
        this.executeAt += delay;
    }

    public long getExecuteAt()
    {
        return this.executeAt;
    }
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(executeAt - new Date().getTime(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        NoticeCPTask that = (NoticeCPTask) o;
        return this.executeAt > that.executeAt ? 1
                : (this.executeAt < that.executeAt ? -1 : 0);
    }

    @Override
    public void run() {
        logger.debug("start task,taskId is {}",taskid);
        try{
            NoticeCPRecord noticeCPRecord = noticeCPRecordDao.getById(taskid);
            String noticeResult = rechargeNoticeCPHelper.doNoticeCP(noticeCPRecord.getOrderid(), noticeCPRecord.getTradeid(), null, noticeCPRecord.getOrderFee(), noticeCPRecord.getAppid(), noticeCPRecord.getRechargeResult(), noticeCPRecord.getType(), noticeCPRecord.getChannel());
            if(noticeResult != null && noticeResult.equalsIgnoreCase("success")){
                noticeCPRecordDao.updateResult(taskid, TradeString.RESULT_OK);
            }else {
                noticeCPRecordDao.updateResult(taskid, TradeString.RESULT_FAILED);
            }
        } catch (Exception e){
            logger.error("notice to cp error.", e);
        }
    }
}
