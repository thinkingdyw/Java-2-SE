package com.think.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class LinuxShell implements Shell{

	@Override
	public Process exe(Command cmd) throws IOException {
		Process p = Runtime.getRuntime().exec(cmd.getCmd()); 
		
		return p;
	}
	
	public static void main(String[] args) throws IOException {
		Shell shell = new LinuxShell();
		Process p = shell.exe(new Command() {
			@Override
			public String getCmd() {
				return "ping localhost";
			}
		});
		if(p != null){
			InputStream in = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,Charset.forName("gbk")));
			String line = reader.readLine();
			while(line != null){
				System.out.println(line);
				line = reader.readLine();
			}
			if(reader != null){
				reader.close();
			}
			if(in != null){
				in.close();
			}
		}
	}
}
