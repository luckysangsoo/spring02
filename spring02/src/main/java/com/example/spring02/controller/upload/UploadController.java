package com.example.spring02.controller.upload;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
		String saveName=file.getOriginalFilename();
		logger.info("파일 크기 : " + file.getSize());
		logger.info("컨텐츠 타입 : " + file.getContentType());
		
		File target = new File(uploadPath, saveName);
		// FileCopyUtils.copy( 바이트배열, 파일객체 )
		FileCopyUtils.copy(file.getBytes(), target);
		
		mav.setViewName("upload/uploadResult"); // uploadResult.jsp 로 포워딩
		mav.addObject("saveName", saveName);
		
		return mav;
	}
	
	@RequestMapping(value="/upload/uploadAjax", method=RequestMethod.GET)
	public void iploadAjax(){
		// uploadAjax.jsp로 포워딩
		
	}
	
	// @ResponseBody : view 가 아니라 데이터를 리턴
	@ResponseBody
	@RequestMapping(value="/upload/uploadAjax", method=RequestMethod.POST, produces="text/plan;charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("originalName: "+file.getOriginalFilename());
		logger.info("size: "+file.getSize());
		logger.info("contentType: "+ file.getContentType());
		return new ResponseEntity<String>(
				UploadFileUtils.uploadFile(uploadPath
						,file.getOriginalFilename()
						,file.getBytes())
						,HttpStatus.OK);
	}

}
