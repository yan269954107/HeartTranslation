package com.yanxw.hearttranslation.dict.reader;

import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.utility.Tools;
import com.yanxw.hearttranslation.util.L;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;



public class DictIndexReader {
	
	private static final int WORD_MAX_SIZE = 256;
    private static final int INDEX_MAX_COUNT = 20;
	
	private RandomAccessFile ranFile;
	
	public DictIndexReader(String dictIndexPath) throws IOException{
		
		ranFile = new RandomAccessFile(dictIndexPath, "r");

	}
	
	public String readkeyWordByCacheOffset(long offset) throws IOException{
		ranFile.seek(offset);
		byte word[] = new byte[WORD_MAX_SIZE];
		int index = 0;
		while((word[index++] = (byte) ranFile.read()) != '\0'){
			if(index == WORD_MAX_SIZE){
				break;
			}
		}
		//delete '\0'
		String keyWord = new String(word, 0, index-1, "utf-8");
		return keyWord;
	}
	
	public DictIndex readDictIndexByCacheOffset(long offset,String keyWord) throws IOException{
		DictIndex dictIndex = null;
		for(int i = 0;i<DictCacheIndexReader.ENTR_PER_PAGE;i++){
			if(null != dictIndex){
				offset += dictIndex.getWord().getBytes().length + 1 + 4 + 4;
			}
			dictIndex = readDictIndexByOffset(offset);
			if(dictIndex.getWord().equalsIgnoreCase(keyWord)){
				return dictIndex;
			}
		}
		return null;
	}
	
	public List<DictIndex> readDictIndexsByCacheOffset(long offset,String keyWord) throws IOException{
		List<DictIndex> dictIndexs = new ArrayList<>();
        int maxCount = 0;
		for(int i = 0;i<DictCacheIndexReader.ENTR_PER_PAGE * 2;i++){
			DictIndex dictIndex = readDictIndexByOffset(offset);
			if(checkFuzzyEqual(dictIndex.getWord(),keyWord)){
				dictIndexs.add(dictIndex);
                maxCount++;
                if(maxCount == INDEX_MAX_COUNT){
                    break;
                }
			}else{
				//after find match word && this time not match, break loop
				if(dictIndexs.size() > 0){
					break;
				}
			}
			offset += dictIndex.getWord().getBytes().length + 1 + 4 + 4;
		}
		return dictIndexs;
	}
	
	private boolean checkFuzzyEqual(String indexWord,String keyWord){
		int keyWordLength = keyWord.length();
		if(indexWord.length() < keyWordLength){
			return false;
		}else{
			String fuzzyWord = indexWord.substring(0, keyWordLength);
			boolean result = fuzzyWord.equalsIgnoreCase(keyWord) ?  true :  false;
			return result;
		}
	}
	
	public DictIndex readDictIndexByOffset(long offset) throws IOException{
		String word = readkeyWordByCacheOffset(offset);
		byte offsetBytes[] = new byte[4];
		byte length[] = new byte[4];
		ranFile.read(offsetBytes);
		ranFile.read(length);
		DictIndex dictIndex = new DictIndex(word, Tools.intByteArrayToLong(offsetBytes, true),
				Tools.byteArrayToInt(length, true));
		return dictIndex;
	}
	
	public void close(){
		try {
			ranFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
