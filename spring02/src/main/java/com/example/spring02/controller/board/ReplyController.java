package com.example.spring02.controller.board;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring02.model.board.dto.ReplyVO;
import com.example.spring02.service.board.ReplyService;

// http://localhost:8080/spring02/view.do?bno=1
// http://localhost:8080/spring02/view/1
// http://localhost:8080/spring02/view/2
// 하나의 url이 하나의 값을 리턴 rest 방식 @RestController

@RestController
@RequestMapping("/reply/*")
public class ReplyController {
	
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

}
