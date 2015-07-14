package com.yanxw.hearttranslation.application;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import com.yanxw.hearttranslation.dict.exception.DictZipFormatException;
import com.yanxw.hearttranslation.dict.reader.ConvenientReader;
import com.yanxw.hearttranslation.service.MyService;
import com.yanxw.hearttranslation.util.L;

import java.io.IOException;

/**
 * Created by yanxw on 15-7-9.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        L.d("application onCreate");
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        new AsyncTask<Void,Void,Integer>(){

            @Override
            protected Integer doInBackground(Void... params) {

                try {
                    ConvenientReader reader = ConvenientReader.getInstance();
                    reader.copyDictFile(MyApplication.this);
                    reader.initReader(MyApplication.this);
                    L.d("application init complete");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DictZipFormatException e) {
                    e.printStackTrace();
                }

                return 0;
            }
        }.execute();
    }


}
