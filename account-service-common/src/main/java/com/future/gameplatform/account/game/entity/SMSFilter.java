package com.future.gameplatform.account.game.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-10-12
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */
public class SMSFilter {

    String fromCode;

    List<Keyword> keywords;

    List<KeyValid> keyValids;

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public List<KeyValid> getKeyValids() {
        return keyValids;
    }

    public void setKeyValids(List<KeyValid> keyValids) {
        this.keyValids = keyValids;
    }
}
