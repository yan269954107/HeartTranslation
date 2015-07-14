package com.yanxw.hearttranslation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yanxw.hearttranslation.R;
import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.exception.DictZipFormatException;
import com.yanxw.hearttranslation.dict.reader.ConvenientReader;
import com.yanxw.hearttranslation.util.ViewFinder;

import java.io.IOException;

public class ExplainDialogActivity extends Activity {

    public static final String SEARCH_KEY_WORD = "search_key_word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_dialog);

        ViewFinder finder = new ViewFinder(this);
        TextView textView = finder.find(R.id.txtExplainDialog);

        String keyWord = getIntent().getStringExtra(SEARCH_KEY_WORD);
        ConvenientReader reader = ConvenientReader.getInstance();
        try {
            DictIndex index = reader.queryMatchWordIndex(keyWord);
            if(null != index){
                textView.setText(reader.queryExplainByIndex(index));
            }else{
                textView.setText(getResources().getString(R.string.null_prompt));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DictZipFormatException e) {
            e.printStackTrace();
        }
    }
}
