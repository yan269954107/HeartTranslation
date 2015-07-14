package com.yanxw.hearttranslation.dict.reader;

import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.entity.DictZipHeader;
import com.yanxw.hearttranslation.dict.exception.DictZipFormatException;
import com.yanxw.hearttranslation.dict.utility.FileAccessor;
import com.yanxw.hearttranslation.dict.utility.Tools;
import com.yanxw.hearttranslation.util.L;

import java.io.IOException;
import java.util.zip.GZIPInputStream;



public class DictZipReader {

	private DictZipHeader dictZipHeader;
	
	private String dictZipPath;
	
	public DictZipReader(String dictZipPath) throws IOException, DictZipFormatException {
		this.dictZipHeader = new DictZipHeaderReader().readDictZipHeader(dictZipPath);
		this.dictZipPath = dictZipPath;
	}
	
	public String readWordByIndex(DictIndex index) throws IOException, DictZipFormatException{

		FileAccessor fileAccessor = new FileAccessor(dictZipPath, "r");
		GZIPInputStream gzipInputStream = new GZIPInputStream(fileAccessor);

		int idx = (int)index.getOffset()/this.dictZipHeader.getChunkLength();
		int off = (int)index.getOffset()%this.dictZipHeader.getChunkLength();
		int pos = this.dictZipHeader.getOffsets(idx);

        fileAccessor.seek(pos);
		byte b[] = new byte[off + index.getLength()];
		Tools.fullRead(gzipInputStream, b);
		byte[] word = new byte[index.getLength()];	
        System.arraycopy(b, off, word, 0, index.getLength());

		fileAccessor.close();
		gzipInputStream.close();

		return new String(word,"utf-8");
	}
	
}
