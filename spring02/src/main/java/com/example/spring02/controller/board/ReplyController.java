package com.example.spring02.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.ReplyVO;
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
		System.out.println("요기요기~~~~~~~~~~~~~~~~~~~~~~~~");
		String userid = (String)session.getAttribute("userid");
		vo.setReplyer(userid);
		replyService.create(vo);
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam int bno, ModelAndView mav){
		List<ReplyVO> list = replyService.list(bno);
		System.out.println("댓글 목록 리스트 처리 중....");
		// 뷰의 이름 지정
		// WEB-INF/views/board/reply_list.jsp
		mav.setViewName("board/reply_list");
		// 뷰에 전달할 데이타 저장
		mav.addObject("list", list);
		// reply_list.jsp로 포워딩
		logger.info("reply list"+mav);
		return mav;
	}
	
	//@RestController 는 데이터를 리턴한다.
	//@ResponseBody : 리턴 데이터를 json으로 변환(생략 가능)
	@RequestMapping("list_json.do")
	public List<ReplyVO> list_json(@RequestParam int bno){
		List<ReplyVO> list = replyService.list(bno);
		
		return list;
		
	}

}
