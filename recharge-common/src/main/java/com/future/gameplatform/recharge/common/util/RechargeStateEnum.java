package com.future.gameplatform.recharge.common.util;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
public enum RechargeStateEnum {
    SDK_NOTICE(1), NOTICED_CHANNEL_FAILED(2), NOTICED_CHANNEL_OK(3), CHANNEL_NOTICE(4), NOTICED_CP_FAILED(5), NOTICED_CP_OK(6);

    private int index;

    RechargeStateEnum(int value){
        this.index = value;
    }

    public int getIndex(){
        return this.index;
    }
}
