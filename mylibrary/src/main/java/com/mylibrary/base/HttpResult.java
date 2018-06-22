package com.mylibrary.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-11-10.
 */

public class HttpResult<T> implements Serializable {
    private int status;
    private String state;
    private String msg;
    private String referer;
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "HttpResult{" +
                "status=" + status +
                ", state='" + state + '\'' +
                ", msg='" + msg + '\'' +
                ", referer='" + referer + '\'' +
                ", result=" + result +
                '}';
    }
}
