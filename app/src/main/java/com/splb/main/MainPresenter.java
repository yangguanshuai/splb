package com.splb.main;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.mylibrary.base.BaseActivity;
import com.splb.mvp.BasePresenterImpl;
import com.splb.utils.ActivityCollector;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{



    private Uri SMS_INBOX = Uri.parse("content://sms/");


    @Override
    public void requestPermissions(final BaseActivity pActivity) {
        new RxPermissions(pActivity)
                .requestEach(
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission pPermission) throws Exception {
                        if (pPermission.granted){
                            //用户同意了该权限
                            getSMS();
                        }else if (pPermission.shouldShowRequestPermissionRationale){
                            //用户拒绝了该权限，没有选中不在询问
                            ActivityCollector.finishAll();
                        }else{
                            //用户拒绝了该权限，选中了不在询问
                            AlertDialog.Builder dia = new AlertDialog.Builder(pActivity);
                            dia.setTitle("权限提示");
                            dia.setMessage("为防止App不能正常运行，请允许权限后继续");
                            dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCollector.finishAll();
                                }
                            });
                            dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent localIntent = new Intent();
                                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    if (Build.VERSION.SDK_INT >= 9) {
                                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                        localIntent.setData(Uri.fromParts("package", pActivity.getPackageName(), null));
                                    } else if (Build.VERSION.SDK_INT <= 8) {
                                        localIntent.setAction(Intent.ACTION_VIEW);
                                        localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                                        localIntent.putExtra("com.android.settings.ApplicationPkgName", pActivity.getPackageName());
                                    }
                                    mView.startActivity(localIntent);
                                }
                            });
                            dia.setCancelable(false);
                            AlertDialog alertDialog = dia.create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        }
                    }
                });
    }

    @Override
    public void getSMS() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> pSubscriber) {
                ContentResolver cr = mView.getContext().getContentResolver();
                String[] projection = new String[] {"_id", "address", "person","body", "date", "type" };
                Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
                if (null == cur) {
                    Log.i("ooc","************cur == null");
                    return;
                }
                List<Map<String,String>> list = new ArrayList<>();
                while(cur.moveToNext()) {
                    String number = cur.getString(cur.getColumnIndex("address"));//发件人手机号
                    String name = cur.getString(cur.getColumnIndex("person"));//发件人手机号
                    String body = cur.getString(cur.getColumnIndex("body"));//短信内容
                    String data = cur.getString(cur.getColumnIndex("date"));//日期，long型
                    //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("num",number);
                    map.put("mess",body);
                    map.put("name",name);
                    map.put("data",data);
//                    Log.d("eeeeee","发件人手机号:"+number+"\r\n发件人:"+name+"\r\n短信内容："+body+"\r\n");
                    list.add(map);
                }
//                StringBuilder sb = new StringBuilder();
//                for (Map<String,String> map :list){
//                    Iterator iter = map.entrySet().iterator();
//                    while (iter.hasNext()){
//                        Map.Entry entry = (Map.Entry) iter.next();
//                        String key = (String) entry.getKey();
//                        String value = (String) entry.getValue();
//                        sb.append(key+":"+value+"\r\n");
//                    }
//                    sb.append("*********************\r\n");
//                }

                pSubscriber.onNext("");
                pSubscriber.onCompleted();

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String pS) {
                    }
                });



    }
}
