package com.yanxw.hearttranslation.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.reader.ConvenientReader;
import com.yanxw.hearttranslation.event.ShowExplainEvent;
import com.yanxw.hearttranslation.event.ShowIndexsEvent;
import com.yanxw.hearttranslation.fragment.DictContentFragment;
import com.yanxw.hearttranslation.fragment.DictExplainFragment;
import com.yanxw.hearttranslation.fragment.DictHeaderFragment;
import com.yanxw.hearttranslation.service.MyService;
import com.yanxw.hearttranslation.util.L;
import com.yanxw.hearttranslation.util.ViewFinder;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private DictContentFragment mContentFragment;
    private DictExplainFragment mExplainFragment;

    private boolean isShowIndexs = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState){

            mContentFragment = DictContentFragment.newInstance();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.dict_header,
                    DictHeaderFragment.newInstance(),DictHeaderFragment.TAG);
            transaction.add(R.id.dict_content,
                    mContentFragment,DictContentFragment.TAG);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    private void initView(){

        if (null == mToolbar){
            ViewFinder finder = new ViewFinder(this);

            mToolbar = finder.find(R.id.toolbar);

            setSupportActionBar(mToolbar);

            mDrawerLayout = finder.find(R.id.drawer);
            mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,
                    R.string.drawer_open,R.string.drawer_close);
            mDrawerToggle.syncState();
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            NavigationView navigationView = finder.find(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_setting:

                            break;
                    }
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                Intent intent = new Intent(this, MyService.class);
                stopService(intent);
                ConvenientReader.getInstance().closeReader();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEvent(ShowExplainEvent event){
//        L.d(TAG,"ShowExplainEvent");
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(mContentFragment);
        DictIndex index = event.getDictIndex();
        if (mExplainFragment == null){
            mExplainFragment = DictExplainFragment.newInstance(index);
            transaction.add(R.id.dict_content,mExplainFragment,DictExplainFragment.TAG);
        }else{
            mExplainFragment.showExplain(index);
            transaction.show(mExplainFragment);
        }
        transaction.commit();

        isShowIndexs = false;
    }

    public void onEvent(ShowIndexsEvent event){
//        L.d(TAG,"ShowIndexsEvent");
        mContentFragment.updateIndexs(event.getIndexs());
        if (!isShowIndexs){
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(mExplainFragment);
            transaction.show(mContentFragment);
            transaction.commit();
        }
    }


}
