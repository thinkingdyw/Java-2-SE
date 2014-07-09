package com.think.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo1 {

	public static void main(String[] args) throws FileNotFoundException {
		RandomAccessFile aFile = new RandomAccessFile("test.txt", "rw");
		if(aFile != null){
			FileChannel fc = null;
			try {
				fc = aFile.getChannel();
				ByteBuffer buf = ByteBuffer.allocate(10);
			    int bytesRead = fc.read(buf);
			    while(bytesRead != -1){
			    	System.out.println("Read " + bytesRead);
			    	buf.flip();
			        while(buf.hasRemaining()){
			            System.out.print((char) buf.get());
			        }
			        buf.clear();
			        bytesRead = fc.read(buf);
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					aFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
