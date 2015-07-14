package com.yanxw.hearttranslation.dict.reader;

import com.yanxw.hearttranslation.dict.entity.DictZipHeader;
import com.yanxw.hearttranslation.dict.exception.DictZipFormatException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;


public class DictZipHeaderReader {

	private static final int GZIP_FORMAT_LABEL = 0x1f8b;
	
//	private static final int FTEXT = 1;	// Extra text

	private static final int FHCRC = 2;	// Header CRC

	private static final int FEXTRA = 4;	// Extra field

	private static final int FNAME = 8;	// File name

	private static final int FCOMMENT = 16;	// File comment
	
	public DictZipHeader readDictZipHeader(String dictZipPath) throws IOException, DictZipFormatException{
		
		DictZipHeader zipHeader = new DictZipHeader();
		
		int headerLength;
		
		FileInputStream fis = new FileInputStream(dictZipPath);
		CRC32 crc32 = new CRC32();
		CheckedInputStream cis = new CheckedInputStream(fis, crc32);
		crc32.reset();
		
		//2 byte
		int label = readUShort(cis,true);
		// These have the fixed values 0x1f8b to identify the file as being in gzip format
		if(label != GZIP_FORMAT_LABEL){
			throw new DictZipFormatException("Not  GZIP format");
		}
		//compression method 0-7 are reserved  8 denotes the "deflate"
		if(readUByte(cis) != 8){
			throw new DictZipFormatException("Unknow compression method");
		}
		//1 byte 
		//bit 0   FTEXT
		//bit 1   FHCRC
		//bit 2   FEXTRA
		//bit 3   FNAME
		//bit 4   FCOMMENT
		//5-7 reserved
		int flag = readUByte(cis);
		
		//skip MTIME XFL OS total 6 byte
		byte buf[] = new byte[6];
		cis.read(buf);
		
		headerLength = 10;
		
		if((flag & FEXTRA) == FEXTRA){
			zipHeader.setExtraLength(readUShort(cis, false));
			headerLength += zipHeader.getExtraLength() + 2;
			zipHeader.setSubfieldID1((byte) readUByte(cis));
			zipHeader.setSubfieldID2((byte) readUByte(cis));
			zipHeader.setSubfieldLength(readUShort(cis, false));
			zipHeader.setSubfieldVersion(readUShort(cis, false));
			zipHeader.setChunkLength(readUShort(cis, false));
			zipHeader.setChunkCount(readUShort(cis, false));
			for(int i = 0;i<zipHeader.getChunkCount();i++){
				zipHeader.setChunks(readUShort(cis, false), i);
			}
		}
		
		// Skip optional file name
        if ((flag & FNAME) == FNAME) {
            while (readUByte(cis) != 0) {
            	headerLength++;
            }
            headerLength++;
        }
        // Skip optional file comment
        if ((flag & FCOMMENT) == FCOMMENT) {
            while (readUByte(cis) != 0) {
            	headerLength++;
            }
            headerLength++;
        }
        // Check optional header CRC
        if ((flag & FHCRC) == FHCRC) {
            int v = (int) crc32.getValue() & 0xffff;
            if (readUShort(cis,false) != v) {
                throw new IOException("Corrupt GZIP header");
            }
            headerLength += 2;
        }
		zipHeader.setHeaderLength(headerLength);
		zipHeader.initOffsets();
		
		fis.close();
		cis.close();
		
		return zipHeader;
		
	}
	
	private int readUByte(InputStream in) throws DictZipFormatException, IOException{
		int i = in.read();
		if(i == -1){
			throw new DictZipFormatException("GZIP file incomplete ");
		}
		return i;
	}
	
	private int readUShort(InputStream in,boolean isNetwordOrder) throws IOException, DictZipFormatException{
		if(isNetwordOrder){
			return (readUByte(in) << 8) | readUByte(in);
		}else{
			return readUByte(in) | (readUByte(in) << 8);
		}
	}
}
