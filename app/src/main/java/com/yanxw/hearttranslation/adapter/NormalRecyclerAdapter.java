package com.yanxw.hearttranslation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.util.ViewFinder;

import java.util.List;

/**
 * Created by yanxw on 15-7-8.
 */
public class NormalRecyclerAdapter extends RecyclerView.Adapter<NormalRecyclerAdapter.NormalTextViewHolder>{

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    private List<String> mTips;

    public NormalRecyclerAdapter(Context context,List<String> tips){
        mContext = context;
        mTips = tips;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(
                R.layout.item_dict_tips,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder normalTextViewHolder, int i) {
        normalTextViewHolder.mTextView.setText(mTips.get(i));
    }

    @Override
    public int getItemCount() {
        return mTips == null ? 0 : mTips.size();
    }

    public void addTips(){
        mTips.add("test test");
        notifyItemInserted(1);
    }

    static class NormalTextViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        NormalTextViewHolder(View view) {
            super(view);
            ViewFinder finder = new ViewFinder(view);
            mTextView = finder.find(R.id.txt_tips);
        }
    }

}
