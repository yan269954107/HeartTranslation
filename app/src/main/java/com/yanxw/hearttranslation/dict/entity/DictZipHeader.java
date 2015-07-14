package com.yanxw.hearttranslation.dict.entity;

public class DictZipHeader {

	private int headerLength;
	
	private int extraLength;
	private byte subfieldID1;
	private byte subfieldID2;
	private int subfieldLength;
	private int subfieldVersion;
	private int chunkLength;
	private int chunkCount;
	private int chunks[];
	private int offsets[];

	public int getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
	}

	public int getExtraLength() {
		return extraLength;
	}

	public void setExtraLength(int extraLength) {
		this.extraLength = extraLength;
	}

	public byte getSubfieldID1() {
		return subfieldID1;
	}

	public void setSubfieldID1(byte subfieldID1) {
		this.subfieldID1 = subfieldID1;
	}

	public byte getSubfieldID2() {
		return subfieldID2;
	}

	public void setSubfieldID2(byte subfieldID2) {
		this.subfieldID2 = subfieldID2;
	}

	public int getSubfieldLength() {
		return subfieldLength;
	}

	public void setSubfieldLength(int subfieldLength) {
		this.subfieldLength = subfieldLength;
	}

	public int getSubfieldVersion() {
		return subfieldVersion;
	}

	public void setSubfieldVersion(int subfieldVersion) {
		this.subfieldVersion = subfieldVersion;
	}

	public int getChunkLength() {
		return chunkLength;
	}

	public void setChunkLength(int chunkLength) {
		this.chunkLength = chunkLength;
	}

	public int getChunkCount() {
		return chunkCount;
	}

	public void setChunkCount(int chunkCount) {
		this.chunkCount = chunkCount;
		chunks = new int[chunkCount];
	}

	public void setChunks(int value,int index){
		chunks[index] = value;
	}
	
	public void initOffsets(){
		offsets = new int[chunkCount];
        offsets[0] = headerLength;
        for (int i = 1; i < chunks.length; i++) {
            offsets[i] = offsets[i - 1] + chunks[i - 1];
        }
	}
	
	public int getOffsets(int index){
		return offsets[index];
	}
}
