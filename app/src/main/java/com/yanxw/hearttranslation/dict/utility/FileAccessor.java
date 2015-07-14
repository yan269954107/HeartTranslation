package com.yanxw.hearttranslation.dict.utility;

import com.yanxw.hearttranslation.util.L;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class FileAccessor extends InputStream{

	private RandomAccessFile ranAccessFile;
	
	private int mark = 0;
	
	public FileAccessor(String path,String mode) throws IOException{
		ranAccessFile = new RandomAccessFile(path, mode);
	}
	
	/**
     * Return the current file pointer position.
     * @return current file pointer position
     * @throws IOException
     */
    public int getPos() throws IOException {
        return (int) ranAccessFile.getFilePointer();
    }
	
	@Override
    public int available() throws IOException {
        return (int) (ranAccessFile.length() - getPos());
    }
	
	@Override
	public int read() throws IOException {
		return ranAccessFile.read();
	}
	
	@Override
    public int read(byte b[], int off, int len) throws IOException {
        return ranAccessFile.read(b, off, len);
    }

	@Override
	public void close() throws IOException {
		ranAccessFile.close();
	}
	
	@Override
    public void mark(int markpos) {
        try {
            mark = getPos();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

	@Override
	public boolean markSupported() {
		return true;
	}
	
	@Override
    public void reset() throws IOException {
		ranAccessFile.seek(mark);
    }

    @Override
    public long skip(long n) throws IOException {
        return (long) ranAccessFile.skipBytes((int) n);
    }

    /**
     * See Also.
     * @param pos
     * @throws IOException
     * @see RandomAccessFile#seek
     */
    public void seek(long pos) throws IOException {
    	ranAccessFile.seek(pos);
    }
    
}
