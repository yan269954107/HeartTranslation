package com.yanxw.hearttranslation.event;

import com.yanxw.hearttranslation.dict.entity.DictIndex;

/**
 * Created by yanxw on 15-7-10.
 */
public class ShowExplainEvent {

    private DictIndex mDictIndex;

    public ShowExplainEvent(DictIndex dictIndex){
        mDictIndex = dictIndex;
    }

    public DictIndex getDictIndex() {
        return mDictIndex;
    }
}
