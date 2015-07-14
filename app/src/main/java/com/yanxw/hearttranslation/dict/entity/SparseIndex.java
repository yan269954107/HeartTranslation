package com.yanxw.hearttranslation.dict.entity;

public class SparseIndex {

	private long offset;
	
	private String word;

	public SparseIndex(){
		
	}
	
	public SparseIndex(long offset){
		this.offset = offset;
	}
	
	
	
	public SparseIndex(long offset, String word) {
		super();
		this.offset = offset;
		this.word = word;
	}
	
	

	@Override
	public String toString() {
		return "offset:" + offset + "  word:"+word;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
	
}
