package com.lcomputer.mymvc.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {

	public static final int MAX_POST_SIZE = 1024 * 1000;
	
	public static MultipartRequest uploadFile(HttpServletRequest req, String saveDirectory, int maxPostSize) {
		try {
			//업로드 성공 
			return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");
		}catch(Exception e) {
			//업로드 실패 
			System.out.println("예외 발생");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void download(HttpServletRequest req, HttpServletResponse res, String directory, String file_serv, String file_origin) {
		String sDirectory = req.getServletContext().getRealPath(directory);
		
		try {
			File file = new File(sDirectory, file_serv);
			InputStream iStream = new FileInputStream(file);
			
			String client = req.getHeader("User-Agent");
			if(client.indexOf("WOW64") == -1) {
				file_origin = new String(file_origin.getBytes("UTF-8"), "ISO-8859-1");
			}else {
				file_origin = new String(file_origin.getBytes("KSC5601"), "ISO-8859-1");
			}
			
			res.reset();
			res.setContentType("application/octet-stream");
			res.setHeader("Content-Disposition", "attachment; fileName=\"" + file_origin + "\"");
			res.setHeader("Content-Length", "" + file.length());
			
			OutputStream oStream = res.getOutputStream();
			
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			while( (readBuffer = iStream.read(b))>0) {
				oStream.write(b,0,readBuffer);
			}
			
			iStream.close();
			oStream.close();
			
			
		}catch(FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없음 ");
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
}
