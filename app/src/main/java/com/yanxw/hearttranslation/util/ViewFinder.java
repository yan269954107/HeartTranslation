package com.yanxw.hearttranslation.util;

import android.app.Activity;
import android.view.View;
import android.view.Window;

/**
 * Created by yanxw on 15-7-7.
 */
public class ViewFinder {

    private static String TAG = "ViewFinder";

    private interface FindWrapper{
        View findViewById(int id);
    }

    private class WindowWrapper implements FindWrapper{

        private Window mWindow;

        public WindowWrapper(final Window window){
            this.mWindow = window;
        }

        @Override
        public View findViewById(int id) {
            return mWindow.findViewById(id);
        }
    }

    private class ViewWrapper implements FindWrapper{

        private View mView;

        public ViewWrapper(final View view){
            this.mView = view;
        }


        @Override
        public View findViewById(int id) {
            return mView.findViewById(id);
        }
    }

    private FindWrapper mWrapper;

    public ViewFinder(Window window){
        mWrapper = new WindowWrapper(window);
    }

    public ViewFinder(View view){
        mWrapper = new ViewWrapper(view);
    }

    public ViewFinder(Activity activity){
        this(activity.getWindow());
    }

    public <E extends View> E find(int id){
        try {
            return (E) mWrapper.findViewById(id);
        }catch (ClassCastException e){
            L.e(TAG,"ClassCastException");
            throw e;
        }
    }

}
