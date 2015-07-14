package com.yanxw.hearttranslation.Model;

/**
 * Created by yanxw on 15-7-8.
 */
public class WordsExplain {

    private String word;
    private String explain;

    public WordsExplain(){

    }

    public WordsExplain(String word,String explain){
        this.word = word;
        this.explain = explain;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(word);
        sb.append("  ");
        sb.append(explain);
        return sb.toString();
    }

    public int getWordLeng(){
        return word.length();
    }

//    public int getExplainLength(){
//        return explain.length();
//    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
