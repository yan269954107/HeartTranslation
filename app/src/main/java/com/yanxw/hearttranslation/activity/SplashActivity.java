package com.yanxw.hearttranslation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.util.ViewFinder;

public class SplashActivity extends Activity {

    private TextView txtAppDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ViewFinder finder = new ViewFinder(this);
        txtAppDescription = finder.find(R.id.txtAppDescription);

        Animation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(1000);
        txtAppDescription.setAnimation(alphaAnimation);

        new AsyncTask<Void,Void,Integer>(){

            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {

                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        }.execute();
    }
}
