package com.example.spring02.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import com.example.spring02.controller.upload.MediaUtils;

public class UploadFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	private static String[] paths;
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData)
		throws Exception {
		//uuid 발급 ( 중복되지 않게 )
		UUID uid=UUID.randomUUID();
		String savedName=uid.toString()+"_"+originalName;
		// 업로드할 디렉토리 생성
		String savedPath=calcPath(uploadPath);
		System.out.println("savedPath : " + savedPath);
		// 임시 디렉토리에 업로드된 파일을 지정된 디렉토리로 복사
		File target=new File(uploadPath+savedPath,savedName);
		System.out.println("target : " + target);
		FileCopyUtils.copy(fileData, target);
		
		// 파일의 확장자 검색
		// a.jpg aaa.bbb.ccc.jpg 마지막 마침표를 찾아서 확장자 검출
		String formatName=originalName.substring(originalName.lastIndexOf(".")+1);
		String uploadFileName= null;
		// 이미지 파일은 썸네일 사용
		if(MediaUtils.getMediaType(formatName) !=null) {
			uploadFileName = makeThumbnail(uploadPath, savedPath, savedName );
		} else {
			uploadFileName = makeIcon(uploadPath, savedPath, savedName );
		}
		return uploadFileName;
	}

	private static String makeThumbnail(
			String uploadPath, String path, String fileName) throws Exception {
		// 이미지를 읽기 위한 버퍼
		BufferedImage sourceImg = 
				ImageIO.read(new File(uploadPath + path, fileName));
		// 100 픽셀 단위의 썸네일 생성
		// Scalr.Mode.FIT_TO_HEIGHT 가로, 세로 100 으로 맞춰라.
		BufferedImage destImg = 
				Scalr.resize(sourceImg, 
						Scalr.Method.AUTOMATIC, 
						Scalr.Mode.FIT_TO_HEIGHT, 100); // 100 픽셀로 조정
		// 썸네일의 이름을 원본과 다르게 변경
		String thumbnailName = 
				uploadPath + path + File.separator + "s_" + fileName ;
		
		File newFile = new File(thumbnailName);
		
		//System.out.println("thumbnailName : " + thumbnailName);
		//System.out.println("fileName : " + fileName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		//System.out.println("formatName : " + formatName);
		// 썸네일 생성
		//System.out.println(formatName.toUpperCase());
		ImageIO.write(
				destImg, formatName.toUpperCase(), newFile);
		// 썸네일의 이름을 리턴 함.
		// 윈도우 \, 유닉스(리눅스) /
		return thumbnailName.substring(
				uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	private static String makeIcon(
		String uploadPath, String path, String fileName	) throws Exception {
		String iconName = 
				uploadPath + path + File.separator + fileName;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		System.out.println(uploadPath + " " + yearPath + " " + monthPath + " " + datePath);
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		logger.info(datePath);
		return datePath;
	}
	
	private static void makeDir(
			String uploadPath, String yearPath, String monthPath, String datePath) {
		if( new File(uploadPath+datePath).exists()){
			System.out.println("디렉토리 생성 되어 있어 return 합니다.");
			return;
		}
//		for (String path : paths) {
		    
		    System.out.println("uploadPath : " + uploadPath);
			File dirPath = new File(uploadPath+datePath);
			if(!dirPath.exists()){
				System.out.println("dirPath : " + dirPath);
				dirPath.mkdirs(); // 디렉토리 생성
			}
//		}
	}
}
