package com.yanxw.hearttranslation.dict.reader;

import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.entity.DictInfo;
import com.yanxw.hearttranslation.dict.entity.SparseIndex;
import com.yanxw.hearttranslation.dict.utility.Tools;
import com.yanxw.hearttranslation.util.L;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class DictCacheIndexReader {

	public static final int ENTR_PER_PAGE = 32;

    private static String TAG = "DictCacheIndexReader";
	
	private BufferedInputStream mBufferedInputStream;
	
	private int pages;
	
	private List<SparseIndex> sparseIndexs;
	
	private DictIndexReader indexReader;
	
	public DictCacheIndexReader(InputStream inputStream,DictInfo dictInfo,
			DictIndexReader indexReader) throws IOException{
		mBufferedInputStream = new BufferedInputStream(inputStream);
		pages = (int)((dictInfo.getWordCount() - 1)/ENTR_PER_PAGE + 2);
		readCacheIndex();
		this.indexReader = indexReader;
	}
	
	/**
	 * read the .idx.oft file
	 * the file format  https://code.google.com/p/babiloo/wiki/StarDict_format
	 * First a utf-8 string terminated by '\0', then many 32-bits numbers as 
	 * the wordoffset index, this index is sparse, and "ENTR_PER_PAGE=32", 
	 * they are not stored in network byte order.
	 * @return
	 */
	private void readCacheIndex() throws IOException{
		
		if(null == sparseIndexs){
			
			sparseIndexs = new ArrayList<>(pages);
			
			//read utf-8 string
			char data;
			while((data = (char) mBufferedInputStream.read()) != -1){
				if(data == '\0'){
					break;
				}
			}
			
			//read sparse index
			byte offsets[] = new byte[4];
			while(mBufferedInputStream.read(offsets) != -1){
				SparseIndex index = new SparseIndex(Tools.intByteArrayToLong(offsets, false));
				sparseIndexs.add(index);
//                L.d(TAG,index.getOffset());
			}

			mBufferedInputStream.close();
		}
		
	}

    public DictIndex searchKeyWordOffset(String keyWord) throws IOException{

        return indexReader.readDictIndexByCacheOffset(
                getKeyWordCacheOffset(keyWord), keyWord);

    }

    public List<DictIndex> fuzzySearchKeyWordOffset(String keyWord) throws IOException{

        return indexReader.readDictIndexsByCacheOffset(
                getKeyWordCacheOffset(keyWord), keyWord);

    }

    private long getKeyWordCacheOffset(String keyWord) throws IOException{
        int begin = 0;
        int last = pages;
        int middle;
        SparseIndex sparseIndex;
        while(begin <= last){
            middle = (begin + last)/2;
            sparseIndex = sparseIndexs.get(middle);
            String cacheKeyWord = sparseIndex.getWord();
            if(cacheKeyWord == null){
                cacheKeyWord = indexReader.readkeyWordByCacheOffset(sparseIndex.getOffset());
            }

            if(keyWord.compareToIgnoreCase(cacheKeyWord) == 0){
//				return sparseIndex.getOffset();
                return sparseIndex.getOffset();
            }else if(keyWord.compareToIgnoreCase(cacheKeyWord) > 0){
                //end and not find keyword
                if(begin == last){
                    //keyword > cacheKeyWord   return last offset
//					return sparseIndexs.get(last).getOffset();
                    return sparseIndexs.get(last).getOffset();
                }
                begin = middle + 1;
            }else{
                if(begin == last){
                    //keyword < cacheKeyWord   return last-1 offset
//					return sparseIndexs.get(last - 1).getOffset();
                    return sparseIndexs.get(last-1).getOffset();
                }
                last = middle - 1;
            }
        }
        //this situation last < begin return last offset
        if(last < 0){
            last = 0;
        }
        return sparseIndexs.get(last).getOffset();
    }
	
}
