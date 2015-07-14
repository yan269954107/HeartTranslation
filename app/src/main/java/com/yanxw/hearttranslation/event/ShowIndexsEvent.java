package com.yanxw.hearttranslation.event;

import com.yanxw.hearttranslation.dict.entity.DictIndex;

import java.util.List;

/**
 * Created by yanxw on 15-7-9.
 */
public class ShowIndexsEvent {

    private List<DictIndex> indexs;

    public ShowIndexsEvent(List<DictIndex> indexs){
        this.indexs = indexs;
    }

    public List<DictIndex> getIndexs(){
        return indexs;
    }

}
