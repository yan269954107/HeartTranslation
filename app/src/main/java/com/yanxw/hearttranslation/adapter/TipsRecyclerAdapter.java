package com.yanxw.hearttranslation.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanxw.hearttranslation.Model.WordsExplain;
import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.util.ViewFinder;

import java.util.List;

/**
 * Created by yanxw on 15-7-8.
 */
public class TipsRecyclerAdapter extends RecyclerView.Adapter<TipsRecyclerAdapter.TipsViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DictIndex> indexs;

    OnItemClickListener mItemClickListener;

    public TipsRecyclerAdapter(Context context,List<DictIndex> indexs){
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.indexs = indexs;
    }

    @Override
    public TipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TipsViewHolder(mLayoutInflater.inflate(R.layout.item_dict_tips,
                parent,false));
    }

    @Override
    public void onBindViewHolder(TipsViewHolder holder, int position) {
        DictIndex index = indexs.get(position);
//        SpannableString ss = new SpannableString(word.toString());
//        int size = mContext.getResources().getDimensionPixelSize(R.dimen.size_tips_word);
//        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
//        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
//        ss.setSpan(sizeSpan,0,word.getWordLeng(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(styleSpan,0,word.getWordLeng(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mTextView.setText(index.getWord());
    }

    @Override
    public int getItemCount() {
        return indexs == null ? 0 : indexs.size();
    }

    public void updateWords(List<DictIndex> indexs){
        this.indexs = indexs;
        notifyDataSetChanged();
    }

    public class TipsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextView;

        public TipsViewHolder(View itemView) {
            super(itemView);
            ViewFinder finder = new ViewFinder(itemView);
            mTextView = finder.find(R.id.txt_tips);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != indexs){
                mItemClickListener.onItemClick(indexs.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(DictIndex index);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
