package com.example.spring02.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring02.model.board.dto.BoardVO;

//@Controller -- view(.JSP) 화면을 리턴
//@RestController -- 데이터를 리턴 , 이기종간에는 데이터만 보내고자 할떄 사용, 파싱

//@Controller
@RestController
@RequestMapping("/sample/*")
public class SampleController {
	@RequestMapping("/hello")
	public String sayHello(){
		return "Hello World";
	}

	@RequestMapping("/sendVO")
	//@ResponseBody // 객체를 json으로 변환, @Controller 로 위에 선언했을 때는 같이 사용해야 함. 
	//@RestController 를 사용할 때는  @ResponseBody는 사용하지 않아도 된다.
	public BoardVO sendVO(){
		BoardVO vo = new BoardVO();
		vo.setBno(1);
		vo.setWriter("shim");
		vo.setContent("내용 ......");
		return vo;
	}
	
	@RequestMapping("/sendList")
	public List<BoardVO> sendList(){
		// ArrayList 객체 생성
		List<BoardVO> items=new ArrayList<>();
		for(int i=1; i<=10; i++){
			BoardVO vo=new BoardVO();
			vo.setBno(i);
			vo.setWriter("shim");
			vo.setContent("내용....."+i);
			items.add(vo);
		}
		return items;
	}
	
	/*@RequestMapping("/sendMap")
	public Map<Integer,BoardVO> sendMap(){
		// Map<key, value>
		Map<Integer,BoardVO> map =new HashMap<Integer,BoardVO>();
		for(int i=1; i<=5; i++){
			BoardVO vo=new BoardVO(); // vo 객체 생성
			vo.setBno(i);
			vo.setWriter("shim");
			vo.setContent("내용....."+i);
			map.put(i,vo); // 맵네 vo 추가
		}
		return map;
	}*/
	
	// ResponseEntity : map을 전송하다가 500 상태코드가 리턴된다면...에러 메세지도 함께 보내고자 할깨 사용
	// ResponseEntity : 데이터 + 에러메세지도 같이 전달 해주는 역활
	@RequestMapping("/sendMap")
	public ResponseEntity<Map<Integer,BoardVO>> sendMap(){
		// Map<key, value>
		Map<Integer,BoardVO> map =new HashMap<Integer,BoardVO>();
		for(int i=1; i<=5; i++){
			BoardVO vo=new BoardVO(); // vo 객체 생성
			vo.setBno(i);
			vo.setWriter("shim");
			vo.setContent("내용....."+i);
			map.put(i,vo); // 맵네 vo 추가
		}
		//return map;
		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
