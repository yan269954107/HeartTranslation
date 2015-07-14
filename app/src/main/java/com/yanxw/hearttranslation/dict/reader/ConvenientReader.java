package com.yanxw.hearttranslation.dict.reader;

import android.content.Context;
import android.content.res.AssetManager;

import com.yanxw.hearttranslation.dict.entity.DictIndex;
import com.yanxw.hearttranslation.dict.entity.DictInfo;
import com.yanxw.hearttranslation.dict.exception.DictZipFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by yanxw on 15-7-9.
 */
public class ConvenientReader {

    private static String DICT_DIRECTOR = "langdao-ec";

    private static String DICT_INFO_PATH = "langdao-ec-gb.ifo";

    private static String DICT_INDEX_PATH = "langdao-ec-gb.idx";

    private static String DICT_CACHE_INDEX_PATH = "langdao-ec-gb.idx.oft";

    private static String DICT_GZIP_DATA_PATH = "langdao-ec-gb.dict.dz";

    private static ConvenientReader sReader;

    private DictZipReader mDictZipReader;

    private DictIndexReader mIndexReader;

    private DictCacheIndexReader mCacheIndexReader;

    private ConvenientReader(){}

    public static ConvenientReader getInstance(){
        if (sReader == null){
            synchronized (ConvenientReader.class){
                if (sReader == null){
                    sReader = new ConvenientReader();
                }
            }
        }
        return sReader;
    }

    public synchronized void initReader(Context context) throws IOException,DictZipFormatException{
        if(mDictZipReader == null){
            AssetManager manager = context.getAssets();
            DictInfo dictInfo = new DictInfoReader().readDictInfo(
                    manager.open(getDir(DICT_INFO_PATH)));
            mIndexReader = new DictIndexReader(
                    context.getFilesDir().getAbsolutePath()+"/"+DICT_INDEX_PATH);
            mCacheIndexReader = new DictCacheIndexReader(
                    manager.open(getDir(DICT_CACHE_INDEX_PATH)),dictInfo,mIndexReader);
            mDictZipReader = new DictZipReader(context.getFilesDir().getAbsolutePath()
                    +"/"+DICT_GZIP_DATA_PATH);
        }
    }

    private String getDir(String fileName){
        return DICT_DIRECTOR + "/" + fileName;
    }

    public void copyDictFile(Context context) throws IOException {
        AssetManager manager = context.getAssets();
        File indexFile = new File(context.getFilesDir(),DICT_INDEX_PATH);
        if (!indexFile.exists()){
            FileOutputStream outputStream = context.openFileOutput(DICT_INDEX_PATH
                    ,Context.MODE_PRIVATE);
            InputStream inputStream = manager.open(getDir(DICT_INDEX_PATH));
            copyFile(inputStream,outputStream);
            outputStream.close();
            inputStream.close();
        }

        File gzipDataFile = new File(context.getFilesDir(),DICT_GZIP_DATA_PATH);
        if (!gzipDataFile.exists()){
            FileOutputStream outputStream = context.openFileOutput(DICT_GZIP_DATA_PATH
                    ,Context.MODE_PRIVATE);
            InputStream inputStream = manager.open(getDir(DICT_GZIP_DATA_PATH));
            copyFile(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
        }

    }

    private void copyFile(InputStream inputStream,OutputStream outputStream){
        byte bytes[] = new byte[1024];
        try {
            while (inputStream.read(bytes) != -1){
                outputStream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DictIndex queryMatchWordIndex(String keyWord) throws IOException{
        return mCacheIndexReader.searchKeyWordOffset(keyWord);
    }

    public List<DictIndex> queryMatchWordIndexs(String keyWord) throws IOException{
        return mCacheIndexReader.fuzzySearchKeyWordOffset(keyWord);
    }

    public String queryExplainByIndex(DictIndex index) throws IOException, DictZipFormatException {
        return mDictZipReader.readWordByIndex(index);
    }

    public void closeReader(){
        mIndexReader.close();
    }

}
