package com.yanxw.hearttranslation.event;

import com.yanxw.hearttranslation.Model.WordsExplain;

import java.util.List;

/**
 * Created by yanxw on 15-7-9.
 */
public class WordExplainEvent {

    private List<WordsExplain> words;

    public WordExplainEvent(List<WordsExplain> words){
        this.words = words;
    }

    public List<WordsExplain> getWords(){
        return words;
    }

}
