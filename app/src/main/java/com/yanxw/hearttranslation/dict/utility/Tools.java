package com.yanxw.hearttranslation.dict.utility;

import com.yanxw.hearttranslation.dict.exception.DictZipFormatException;

import java.io.IOException;
import java.io.InputStream;

public class Tools {

	public static final int byteArrayToInt(byte bytes[],boolean isNetworkOrder){
		
		if(bytes.length != 4){
			throw new IllegalArgumentException("bytes length require 4");
		}
		
		if(isNetworkOrder){
			return bytes[0] << 24 | (bytes[1] & 0xff) << 16 
					| (bytes[2] & 0xff) << 8 | (bytes[3] & 0xff);
		}else{
			return (bytes[0] & 0xff) | (bytes[1] & 0xff) << 8 
					| (bytes[2] & 0xff) << 16 | bytes[3] << 24;
		}
		
	}
	
	public static final long intByteArrayToLong(byte bytes[],boolean isNetworkOrder){
		return byteArrayToInt(bytes,isNetworkOrder) & 0x00000000ffffffffl;
	}
	
	public static final byte[] longToByteArray(long s){
		byte buf[] = new byte[8];
		for(int i = buf.length - 1; i >= 0; i--){
			buf[i] = (byte) (s & 0x00000000000000ff);
			s >>= 8;
			System.out.println(buf[i]);
		}
		return buf;
	}
	
	public static final void fullRead(InputStream input,byte b[]) throws IOException, DictZipFormatException{
		int length = b.length;
		int n = 0;
		while(n < length){
			int count = input.read(b, n, length-n);
			if(count == -1){
				throw new DictZipFormatException("EOF EXCEPTION");
			}
			n += count;
		}
	}
	
	
	public static void main(String[] args) {

		int a = 4/2;
		System.out.println(a);
		
	}
	
}
