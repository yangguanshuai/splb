package com.mylibrary.http.cookie;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CookieResulte {

    @Id
    private Long id;
    //url
    private String url;
    //返回结果
    private String result;
    //时间
    private long time;

    public CookieResulte(String pUrl, String pResult, long pTime) {
        url = pUrl;
        result = pResult;
        time = pTime;
    }

    @Generated(hash = 1746685267)
    public CookieResulte(Long id, String url, String result, long time) {
        this.id = id;
        this.url = url;
        this.result = result;
        this.time = time;
    }
    @Generated(hash = 2104390000)
    public CookieResulte() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
