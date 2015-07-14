package com.yanxw.hearttranslation.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.yanxw.hearttranslation.Model.WordsExplain;
import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.adapter.NormalRecyclerAdapter;
import com.yanxw.hearttranslation.adapter.TipsRecyclerAdapter;
import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.event.ShowExplainEvent;
import com.yanxw.hearttranslation.event.ShowIndexsEvent;
import com.yanxw.hearttranslation.event.WordExplainEvent;
import com.yanxw.hearttranslation.util.L;
import com.yanxw.hearttranslation.util.ViewFinder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictContentFragment extends Fragment implements TipsRecyclerAdapter.OnItemClickListener{

    public final static String TAG = "DictContentFragment";

    private RecyclerView mRecyclerView;

    private TipsRecyclerAdapter mAdapter;

    public static DictContentFragment newInstance() {
        return new DictContentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dict_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ViewFinder finder = new ViewFinder(view);
        mRecyclerView = finder.find(R.id.recycler_dict_tips);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<DictIndex> indexs = new ArrayList<>();

        mAdapter = new TipsRecyclerAdapter(getActivity(),indexs);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        super.onViewCreated(view, savedInstanceState);
    }

    public void updateIndexs(List<DictIndex> indexs){
        mAdapter.updateWords(indexs);
    }

    @Override
    public void onItemClick(DictIndex index) {
//        L.d(index.toString());
        EventBus.getDefault().post(new ShowExplainEvent(index));
    }
}
