package com.mylibrary.base;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 完成客户端bug的记录本地工作
 */

public class BaseExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e != null){
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String errorReport = stringWriter.toString();
            saveInFile(errorReport);
            Log.d("error","<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\r\n");
            Log.d("error",errorReport+"\r\n");
            Log.d("error","<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\r\n");
        }
    }

    private void saveInFile(String errorReport) {
        FileOutputStream out = null ;
        File file = new File(Environment.getExternalStorageDirectory() , "error-log.txt");

        Log.d("error",file.getAbsolutePath().toString());
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            try {
                out = new FileOutputStream(file);
                out.write(errorReport.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (out != null) try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
