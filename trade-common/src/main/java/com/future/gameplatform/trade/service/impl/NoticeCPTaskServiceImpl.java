package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.trade.dao.NoticeCPRecordDao;
import com.future.gameplatform.trade.entity.NoticeCPRecord;
import com.future.gameplatform.trade.service.NoticeCPTaskService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.DelayQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class NoticeCPTaskServiceImpl implements NoticeCPTaskService {

    private final static Logger logger = LoggerFactory
            .getLogger(NoticeCPTaskServiceImpl.class);

    private final DelayQueue<NoticeCPTask> SCHEDULED_TASK_QUEUE = new DelayQueue<NoticeCPTask>();
    private final Map<String, NoticeCPTask> scheduleMessageTaskMap = new HashMap<String, NoticeCPTask>();

    private int capacity = 10000;
    private int maxInQueueTimeDistance = 7;

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaxInQueueTimeDistance(int maxInQueueTimeDistance) {
        this.maxInQueueTimeDistance = maxInQueueTimeDistance;
    }

    private NoticeCPRecordDao noticeCPRecordDao;

    public void setNoticeCPRecordDao(NoticeCPRecordDao noticeCPRecordDao) {
        this.noticeCPRecordDao = noticeCPRecordDao;
    }

    private RechargeNoticeCPHelper rechargeNoticeCPHelper;

    public void setRechargeNoticeCPHelper(RechargeNoticeCPHelper rechargeNoticeCPHelper) {
        this.rechargeNoticeCPHelper = rechargeNoticeCPHelper;
    }

    private boolean addTask(NoticeCPTask task) {
        if (isOutofCapacity()) {
            return false;
        }
        scheduleMessageTaskMap.put(task.getTaskid(), task);
        SCHEDULED_TASK_QUEUE.put(task);
        logger.debug("add task {} success,task queue size increase to {}",
                task.getTaskid(), SCHEDULED_TASK_QUEUE.size());
        return true;
    }

    private boolean isOutofCapacity() {
        if (SCHEDULED_TASK_QUEUE.size() >= capacity) {
            logger.warn("out of capacity {}", SCHEDULED_TASK_QUEUE.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean setupTask(NoticeCPTask task) {
        if (task.getDelayValue() > maxInQueueTimeDistance) {
            logger.debug("{} delay value great than maxInQueueTimeDistance,so ignore.",task.getTaskid());
            return false;
        }
        return addTask(task);
    }

    @Override
    public void removeTask(String taskid) {
        logger.debug("remove task {} success,task queue size {}", taskid,
                SCHEDULED_TASK_QUEUE.size());
        NoticeCPTask scheduledTask = scheduleMessageTaskMap
                .remove(taskid);
        SCHEDULED_TASK_QUEUE.remove(scheduledTask);
        logger.debug("remove task {} success,task queue size {}", taskid,
                SCHEDULED_TASK_QUEUE.size());
    }

    @Override
    public NoticeCPTask getTimeToExecuteTask() {
        logger.debug("[ScheduledTaskService] getTimeoutTask,size ={}",
                SCHEDULED_TASK_QUEUE.size());
        NoticeCPTask task = SCHEDULED_TASK_QUEUE.poll();

        if (task == null) {
            logger.debug("No timeout task!");
        } else {
            scheduleMessageTaskMap.remove(task.getTaskid());
            logger.info("Message {} delay timeout!", task.getTaskid());
        }
        return task;
    }

    @Override
    public void loadTaskFromDbGradually() {
        logger.debug("load task from db start");

        if (isOutofCapacity()) {
            return;
        }

        int loadNum = capacity - SCHEDULED_TASK_QUEUE.size();
        logger.debug("load task from db,capacity:[{}], queue size:[{}], loadnum:[{}]",new Object[]{capacity,SCHEDULED_TASK_QUEUE.size(), loadNum});
        List<NoticeCPRecord> noticeCPRecordList = noticeCPRecordDao.listPendingRecord();
        if(noticeCPRecordList != null){
            logger.debug("load task from db,load count[{}]",noticeCPRecordList.size());
        }
        Iterator<NoticeCPRecord> noticeCPRecordIterator = noticeCPRecordList.iterator();
        int i = 0;
        while(noticeCPRecordIterator.hasNext() && i < loadNum){
            NoticeCPRecord noticeCPRecord = noticeCPRecordIterator.next();
            addTask(new NoticeCPTask(noticeCPRecord.getId(), 0, noticeCPRecordDao, rechargeNoticeCPHelper));
            i++;
        }
        logger.debug("load task from db finished");
    }

    @Override
    public void loadUnfinishedTaskFromDb() {

    }

    @Override
    public void releaseNoticeCPTask() {
        logger.debug("no implements, no function need to manipulate.");

    }
}
