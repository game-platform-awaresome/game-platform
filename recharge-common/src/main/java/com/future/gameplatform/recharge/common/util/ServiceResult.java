package com.future.gameplatform.recharge.common.util;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class ServiceResult<E> {

    private boolean isSuccess;

    private int errorCode;

    private String errorMessage;

    private E value;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return "success:" + this.isSuccess() +" error code:"+this.getErrorCode()+" error msg:"+this.getErrorMessage()+" value:"+this.getValue();
    }
}
