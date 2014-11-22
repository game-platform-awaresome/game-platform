package com.future.gameplatform.trade.service;

import com.future.gameplatform.trade.service.impl.NoticeCPTask;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface NoticeCPTaskService {

    /**
     * @param task
     */
    public boolean setupTask(NoticeCPTask task);

    /**
     * @param taskid
     */
    public void removeTask(String taskid);

    /**
     * @return
     */
    public NoticeCPTask getTimeToExecuteTask();

    /**
     *
     */
    public void loadTaskFromDbGradually();

    /**
     *
     */
    public void loadUnfinishedTaskFromDb();

    /**
     *
     */
    public void releaseNoticeCPTask();
}
