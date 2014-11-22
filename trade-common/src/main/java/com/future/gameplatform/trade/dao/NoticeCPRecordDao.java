package com.future.gameplatform.trade.dao;

import com.future.gameplatform.trade.entity.NoticeCPRecord;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface NoticeCPRecordDao {

    public String save(NoticeCPRecord noticeCPRecord);

    public NoticeCPRecord getById(String id);

    public NoticeCPRecord updateResult(String id, String result);

    public List<NoticeCPRecord> listPendingRecord();

}
