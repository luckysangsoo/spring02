package com.example.spring02.controller.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	// xml에 설정된 리소스 참조 
	// bean의 id가 uploadPath 를 찾는다. servlet-context.xml 에 정의 해 두었다.
	// 파일이 전송 될 디렉토리 지정 ( ex : E:/upload )
	@Resource(name="uploadPath")
	String uploadPath;
	
	
	@RequestMapping(value="/upload/uploadForm", method=RequestMethod.GET)
	public void uploadForm(){
		// upload/uploadForm.jsp로 포워딩
	}

	// 업로드 버튼 => 서버의 임시디렉토리에 업로드 
	// 파일정보가 file 에 저장 => 지정된 디렉토리에 file 에 저장
	@RequestMapping(value="/upload/uploadForm", method=RequestMethod.POST)
	public ModelAndView uploadForm(MultipartFile file, ModelAndView mav)
			throws Exception {
		// upload/uploadForm.jsp로 포워딩
		logger.info("파일 이름 : " + file.getOriginalFilename());
		String savedName=file.getOriginalFilename();
		logger.info("파일 크기 : " + file.getSize());
		logger.info("컨텐츠 타입 : " + file.getContentType());
		
		/*File target = new File(uploadPath, savedName);
		// FileCopyUtils.copy( 바이트배열, 파일객체 )
		FileCopyUtils.copy(file.getBytes(), target);*/
		
		savedName = uploadFile(
				savedName, file.getBytes());
		
		mav.setViewName("upload/uploadResult"); // uploadResult.jsp 로 포워딩
		mav.addObject("savedName", savedName);
		
		return mav;
	}
	
	// 파일 이름이 중복되지 않도록 처리
	private String uploadFile(String originalName,
			byte[] fileData) throws Exception {
	// uuid 생성(Universal Unique Identifier, 범용 고유 식별자)
		UUID uid=UUID.randomUUID();
		String savedName=uid.toString()+"_"+originalName;
		File target=new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target);
		return savedName;
	}
	
	// 파일업로드 ajax 방식
	@RequestMapping(value="/upload/uploadAjax", method=RequestMethod.GET)
	public void iploadAjax(){
		// uploadAjax.jsp로 포워딩
		
	}
	
	// @ResponseBody : view 가 아니라 데이터를 리턴 : json
	@ResponseBody
	@RequestMapping(value="/upload/uploadAjax", 
		method=RequestMethod.POST, produces="text/plan;charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("originalName: "+file.getOriginalFilename());
		logger.info("size: "+file.getSize());
		logger.info("contentType: "+ file.getContentType());
		return new ResponseEntity<String>(
				UploadFileUtils.uploadFile(
						uploadPath
						,file.getOriginalFilename()
						,file.getBytes())
						,HttpStatus.OK);
	}
	
	// 리턴타입 : ResponseEntity<byte[]> 파일이 가야 하는데 데이터 송수신 파일은 배열 타입으로 갑니다.
	// 그리고, 메세지까지 뿌려주기 위해서 ResponseEntity 를 사용한겁니다. 
	@ResponseBody // view가 아닌 data 를 리턴
	@RequestMapping("/upload/displayFile")
	public ResponseEntity<byte[]> displayFile(
			String fileName) throws Exception {
		
		// 서버의 파일을 다운로드 하기 위한 스트림
		InputStream in=null; // java.io -- 파일을 읽기 위해
		ResponseEntity<byte[]> entity=null;
		
		try {
			// 확장자 검색
			String formatName=fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType=MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체
			HttpHeaders headers=new HttpHeaders();
			// InputStream  생성
			in=new FileInputStream(uploadPath+fileName);
			
			if(mType != null){ // 이미지 파일이면
				headers.setContentType(mType);
			}else{ // 이미지 파일이 아니면
				fileName=fileName.substring(fileName.indexOf(".")+1);
				// 다운로드용 컨텐트 타입
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				// 큰 따옴표 내부에 "  \"   "
				// 바이트 배열을 스트링으로 변환 -> uft-8로 -> iso-8859-1 서유럽언어
				// 헤더에는 한글이 허용 안되기 때문에 잠시 변환해서 가야 한글이 깨지지 않는다. Test7.java 참조
				headers.add("Content-Disposition", 
						"attachment; filename=\"" 
				+ new String(fileName.getBytes("utf-8"),"iso-8859-1") + "\""); 
			}
			entity=new ResponseEntity<byte[]>(
					IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	

}
