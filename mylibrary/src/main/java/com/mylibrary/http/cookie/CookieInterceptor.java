package com.mylibrary.http.cookie;

import android.util.Log;

import com.mylibrary.utils.CookieDbUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class CookieInterceptor  implements Interceptor{

    private boolean mCache;
    private String mUrl;
    private CookieDbUtil mCookieDbUtil;

    public CookieInterceptor( boolean pCache, String pUrl) {
        mCookieDbUtil = CookieDbUtil.getInstance();
        mCache = pCache;
        mUrl = pUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if(mCache){
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            Log.d("cookie",bodyString);
            CookieResulte resulte = mCookieDbUtil.queryCookieBy(mUrl);
            long time=System.currentTimeMillis();
            /*保存和更新本地数据*/
            if(resulte==null){
                resulte  =new CookieResulte(mUrl,bodyString,time);
                mCookieDbUtil.saveCookie(resulte);
            }else{
                resulte.setResult(bodyString);
                resulte.setTime(time);
                mCookieDbUtil.updateCookie(resulte);
            }
        }
        return response;
    }
}
