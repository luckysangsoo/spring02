package com.example.spring02.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.ReplyVO;
import com.example.spring02.service.board.Pager;
import com.example.spring02.service.board.ReplyService;

// http://localhost:8080/spring02/view.do?bno=1

//REST : REpresentational State Transfer
// 하나의 URI가 하나의 고유한 리소스를 대표하도록 설계된 개념
// http://localhost:8080/spring02/view/1
// http://localhost:8080/spring02/view/2
// 하나의 url이 하나의 값을 리턴 rest 방식 @RestController
// 스프링 4.0부터 지원
// @Controller 는 마지막에 view로 넘어가지만 
// @RestController 는 마지막에 데이타를 리턴한다. ajax와 같이 사용 함.

//@RestController
//@Controller
@RestController
@RequestMapping("/reply/*")
public class ReplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Inject
	ReplyService replyService;
	
	@RequestMapping("insert.do")
	public void insert(@ModelAttribute ReplyVO vo,
			HttpSession session){
		System.out.println("댓글 insert ~~~~~~~~~~~");
		String userid = (String)session.getAttribute("userid");
		vo.setReplyer(userid);
		replyService.create(vo);
	}
	
	// ResponseEntity : 데이터 + http status code를 같이 보내 줌
	// @ResponseBody : 객체를 json 으로 변환
	// @RequestBody : json을 입력데이터로 받음(json은 String ) json을 객체로
	@RequestMapping(value="insert_rest.do", method=RequestMethod.POST)
	public ResponseEntity<String> insert_rest(
			@RequestBody ReplyVO vo,
			HttpSession session){
		ResponseEntity<String> entity=null;
		try{
			System.out.println("댓글 insert ~~~~~~~~~~~");
			//String userid="shim";
			String userid = (String)session.getAttribute("userid");
			vo.setReplyer(userid);
			replyService.create(vo);
			
			entity=new ResponseEntity<String>(
					"success", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(
					e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	/*{
		  "replytext":"댓글을 추가합니다.",
		  "replyer":"shim",
		  "bno":"997",
		  "secret_reply":"n"
		}
	    bno key, replyer 테이블에 있는 값을 넣어야 한다  
	     구글 앱에서 ARC 로 테스트 해 본다.*/
	
	
	@RequestMapping("list.do")
	public ModelAndView list(			
			@RequestParam int bno, 
			@RequestParam(defaultValue="1") int curPage,
			ModelAndView mav,
			HttpSession session){
		int count=replyService.count(bno); // 댓글 갯수
		Pager pager=new Pager(count,curPage);
		int start=pager.getPageBegin();
		int end=pager.getPageEnd();
				
		List<ReplyVO> list = replyService.list(bno, start, end, session);
		System.out.println("댓글 목록 리스트 처리 중....");
		// 뷰의 이름 지정
		// WEB-INF/views/board/reply_list.jsp
		mav.setViewName("board/reply_list");
		// 뷰에 전달할 데이타 저장
		mav.addObject("list", list);
		mav.addObject("pager", pager);
		// reply_list.jsp로 포워딩
		logger.info("reply list"+mav);
		return mav;
	}
	
	//@RestController 는 데이터를 리턴한다.
	//@ResponseBody : 데이터를 json으로 변환(생략 가능)
	@RequestMapping("list_json.do")
	public List<ReplyVO> list_json( @RequestParam int bno ){
		List<ReplyVO> list = replyService.json_list(bno);
		return list;
	}

	// http://localhost:8888/spring02/reply/list/997/1
	// 997/1 : 게시물 번호/페이지번호
	
	@RequestMapping(value="/list/{bno}/{curPage}",
			method=RequestMethod.GET)
	public ModelAndView reply_list(
			@PathVariable("bno") int bno,
			@PathVariable int curPage,
			ModelAndView mav,
			HttpSession session){
		int count=replyService.count(bno); // 댓글 갯수
		Pager pager=new Pager(count,curPage);
		int start=pager.getPageBegin();
		int end=pager.getPageEnd();
				
		List<ReplyVO> list = replyService.list(bno, start, end, session);
		System.out.println("댓글 목록 리스트 처리 중....");
		// 뷰의 이름 지정
		// WEB-INF/views/board/reply_list.jsp
		mav.setViewName("board/reply_list");
		// 뷰에 전달할 데이타 저장
		mav.addObject("list", list);
		mav.addObject("pager", pager);
		// reply_list.jsp로 포워딩
		logger.info("reply list"+mav);
		return mav;
	}
	
	// @PathVariable : url에 입력될 변수값 지정
	@RequestMapping(value="/detail/{rno}",
			 method=RequestMethod.GET)
	public ModelAndView reply_detail(
			@PathVariable("rno") int rno,
			ModelAndView mav){
		ReplyVO vo = replyService.detail(rno);
		mav.setViewName("board/reply_detail");
		mav.addObject("vo", vo);
		return mav;
		
	}
	
	// @RequestBody : json을 입력데이터로 받음(json은 String ) json을 객체로
	@RequestMapping(value="/update/{rno}",
			 method={ RequestMethod.PUT, RequestMethod.PATCH} )
	public ResponseEntity<String> update(
			@PathVariable("rno") int rno,
			@RequestBody ReplyVO vo ) {
		
		ResponseEntity<String> entity=null;
		try {
			vo.setRno(rno);
			
			//서비스 호출
			replyService.update(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	@RequestMapping(value="/delete/{rno}",
			 method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("rno") int rno
			){
		
		ResponseEntity<String> entity=null;
		try {
			//서비스 호출
			replyService.delete(rno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;		
	}
	
	
	
}
