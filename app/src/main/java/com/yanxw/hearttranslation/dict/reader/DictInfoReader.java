package com.yanxw.hearttranslation.dict.reader;

import com.yanxw.hearttranslation.dict.entity.DictInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DictInfoReader {

	public DictInfo readDictInfo(InputStream in) throws IOException{
		
		DictInfo dictInfo = new DictInfo();

		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader bReader = new BufferedReader(reader);
		String line;
		String info[];
		while((line = bReader.readLine()) != null){
			info = line.split("=");
			if(info.length == 2){
				
				switch (info[0]) {
				case "wordcount":
					dictInfo.setWordCount(Long.parseLong(info[1]));
					break;
				case "idxfilesize":
					dictInfo.setIdxFileSize(Long.parseLong(info[1]));
					break;
				case "sametypesequence":
					dictInfo.setSameTypeSequence(info[1]);
					break;
				}
				
			}
		}
		
		bReader.close();
		
		return dictInfo;
		
	}
	
}
