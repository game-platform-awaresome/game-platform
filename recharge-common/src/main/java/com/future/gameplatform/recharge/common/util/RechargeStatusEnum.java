package com.future.gameplatform.recharge.common.util;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public enum RechargeStatusEnum {

    OK(1),DELETED(0);

    private int index;

    RechargeStatusEnum(int value){
        this.index = value;
    }

    public int getIndex(){
        return this.index;
    }

}
