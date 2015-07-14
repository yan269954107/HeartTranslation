package com.yanxw.hearttranslation.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.activity.MainActivity;
import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.exception.DictZipFormatException;
import com.yanxw.hearttranslation.dict.reader.ConvenientReader;
import com.yanxw.hearttranslation.util.L;
import com.yanxw.hearttranslation.util.ViewFinder;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictExplainFragment extends Fragment {

    public final static String TAG = "DictExplainFragment";

    public final static String QUERY_WORD_INDEX = "query_word_index";

    private TextView txtExplain;

    public static DictExplainFragment newInstance(DictIndex index) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(QUERY_WORD_INDEX, index);
        DictExplainFragment fragment = new DictExplainFragment();
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dict_explain, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle argument = getArguments();

        if (null != argument){

            DictIndex index = argument.getParcelable(QUERY_WORD_INDEX);
            ViewFinder finder = new ViewFinder(view);
            txtExplain = finder.find(R.id.txtExplain);
            setExplain(index);

        }

        super.onViewCreated(view, savedInstanceState);
    }

    public void showExplain(DictIndex index){
        setExplain(index);
    }

    private void setExplain(DictIndex index){
        L.d(TAG,index.toString());
        try {
            if (null != txtExplain){
                String explain = ConvenientReader.getInstance().queryExplainByIndex(index);
                txtExplain.setText(explain);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DictZipFormatException e) {
            e.printStackTrace();
        }
    }
}
