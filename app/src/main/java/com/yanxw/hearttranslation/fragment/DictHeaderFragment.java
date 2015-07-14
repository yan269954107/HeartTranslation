package com.yanxw.hearttranslation.fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yanxw.hearttranslation.Model.WordsExplain;
import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.reader.ConvenientReader;
import com.yanxw.hearttranslation.event.ShowExplainEvent;
import com.yanxw.hearttranslation.event.ShowIndexsEvent;
import com.yanxw.hearttranslation.event.WordExplainEvent;
import com.yanxw.hearttranslation.util.L;
import com.yanxw.hearttranslation.util.ViewFinder;
import com.yanxw.hearttranslation.widget.ClearEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictHeaderFragment extends Fragment {

    public final static String TAG = "DictHeaderFragment";

    private ClearEditText mEditText;

    public static DictHeaderFragment newInstance(){
        return new DictHeaderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dict_header, container, false);
        ViewFinder finder = new ViewFinder(view);
        mEditText = finder.find(R.id.edit_words_input);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        ViewFinder finder = new ViewFinder(view);
        EditText editText = finder.find(R.id.edit_words_input);
        editText.addTextChangedListener(textWatcher);

        super.onViewCreated(view, savedInstanceState);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            L.d("onTextChanged:%s", s);
            try {
                String keyWord = s.toString().trim();
                List<DictIndex> indexs;
                if (keyWord.length() > 0){
                    indexs = ConvenientReader.getInstance()
                            .queryMatchWordIndexs(s.toString());
                }else{
                    indexs = new ArrayList<>();
                }
                ShowIndexsEvent event = new ShowIndexsEvent(indexs);
                EventBus.getDefault().post(event);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this,1);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(ShowExplainEvent event){
//        L.d(TAG,"ShowExplainEvent");
        String word = event.getDictIndex().getWord();
        mEditText.removeTextChangedListener(textWatcher);
        mEditText.setText(word);
        mEditText.setSelection(word.length());
        mEditText.addTextChangedListener(textWatcher);
//        mEditText.clearFocus();
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
//                Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mEditText.getWindowToken(),0);
    }
}
