package com.yanxw.hearttranslation.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.activity.ExplainDialogActivity;
import com.yanxw.hearttranslation.util.L;

public class MyService extends Service {

    private static final String TAG = "MyService";

    private int notificationId = 1;

    private ClipboardManager mClipboardManager;

    private String previousWord = "";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(getResources().getString(R.string.notify_content_title));

        startForeground(notificationId,builder.build());

        if (null == mClipboardManager){
            L.d(TAG,"start listener");
            mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            mClipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    L.d(TAG,"onPrimaryClipChanged");
                    if (mClipboardManager.hasPrimaryClip()){
                        ClipData data = mClipboardManager.getPrimaryClip();
                        ClipData.Item item = data.getItemAt(0);
                        String text = item.coerceToText(MyService.this).toString().trim();

                        if (!previousWord.equals(text) && text.length() > 0){
                            Intent i = new Intent(MyService.this, ExplainDialogActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra(ExplainDialogActivity.SEARCH_KEY_WORD,text);
                            startActivity(i);
                            previousWord = text;
                        }

                        L.d(TAG,"clipdata 0 : %s",text);

                    }
                }
            });
        }


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }
}
