package com.yanxw.hearttranslation.dict.entity;

public class DictInfo {

	private long wordCount;

	private long idxFileSize;

	private String sameTypeSequence;

	public long getWordCount() {
		return wordCount;
	}

	public void setWordCount(long wordCount) {
		this.wordCount = wordCount;
	}

	public long getIdxFileSize() {
		return idxFileSize;
	}

	public void setIdxFileSize(long idxFileSize) {
		this.idxFileSize = idxFileSize;
	}

	public String getSameTypeSequence() {
		return sameTypeSequence;
	}

	public void setSameTypeSequence(String sameTypeSequence) {
		this.sameTypeSequence = sameTypeSequence;
	}

	@Override
	public String toString() {
		return "wordCount:" + wordCount + "\nidxFileSize:" + idxFileSize
				+ "\nsameTypeSequence:" + sameTypeSequence;
	}

}
