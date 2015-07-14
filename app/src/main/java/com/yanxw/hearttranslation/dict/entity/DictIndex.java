package com.yanxw.hearttranslation.dict.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class DictIndex implements Parcelable{

	private String word;
	private long offset;
	private int length;
	public DictIndex(String word, long offset, int length) {
		super();
		this.word = word;
		this.offset = offset;
		this.length = length;
	}

	protected DictIndex(Parcel in) {
		word = in.readString();
		offset = in.readLong();
		length = in.readInt();
	}

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	@Override
	public String toString() {
		return "DictIndex [word=" + word + ", offset=" + offset + ", length="
				+ length + "]";
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(word);
		dest.writeLong(offset);
		dest.writeInt(length);
	}

	public static final Creator<DictIndex> CREATOR = new Creator<DictIndex>() {
		@Override
		public DictIndex createFromParcel(Parcel in) {
			return new DictIndex(in);
		}

		@Override
		public DictIndex[] newArray(int size) {
			return new DictIndex[size];
		}
	};
}
