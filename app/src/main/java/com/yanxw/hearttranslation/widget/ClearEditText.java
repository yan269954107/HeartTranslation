package com.yanxw.hearttranslation.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.util.L;

/**
 * Created by yanxw on 15-7-8.
 */
public class ClearEditText extends EditText implements TextWatcher{

    private Drawable mClearDrawable;
    private boolean mClearShow = false;

    public ClearEditText(Context context){
        this(context,null);
    }

    public ClearEditText(Context context,AttributeSet attrs){
        this(context,attrs,android.R.attr.editTextStyle);
    }

    @SuppressWarnings("deprecation")
    public ClearEditText(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        mClearDrawable = getResources().getDrawable(R.drawable.ic_remove);
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
    }

    private void setClearButtonVisible(boolean visible){
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],
                right,getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0 && !mClearShow){
            mClearShow = true;
            setClearButtonVisible(true);
        }else if (s.length() == 0 && mClearShow){
            mClearShow = false;
            setClearButtonVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return true;
    }
}
