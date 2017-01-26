package com.example.spring02.controller.message;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring02.model.message.dto.MessageVO;
import com.example.spring02.service.message.MessageService;

@RestController
@RequestMapping("/messages/*")
public class MessageController {
	
	@Inject
	MessageService service;
	
	// ResponseEntity : Http Status Code(http 상태코드 ) + 데이터를 전달
	// error message까지 같이 전달
	// @RequestBody : json 으로 데이터가 입력 될 때 , String을 객체로 변환 해준다.
	// @ResponseBody : 서버 => 클라이언트 json으로 전달하고자 할 떄
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> addMessage(
			@RequestBody MessageVO vo){
		
		ResponseEntity<String> entity=null;
		try {
			service.addMessage(vo);
			entity=new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/*
	 * {
		  "targetid":"user01",
		  "sender":"user02",
		  "message":"Good Moning"
		}
		
		@RequestBody MessageVO vo : 위처럼 스트링 데이터를 객체에 담을 수 있도록 @RequestBody 어노테이션을 사용한다.
		*
		*/
	
	
	

}
