package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.spring02.model.board.dao.ReplyDAO;
import com.example.spring02.model.board.dto.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	ReplyDAO replyDao; 
	
	@Override
	public List<ReplyVO> list(Integer bno,int start,int end, HttpSession session) {
		
		List<ReplyVO> items=replyDao.list(bno, start, end);
		//현재 사용자
		String userid=(String)session.getAttribute("userid");
		
		for(ReplyVO vo : items){
			// 비밀 댓글일 때
			if(userid != null && vo.getSecret_reply().equals("y")){
				// 게시물 작성자
				String writer=vo.getWriter();
				// 댓글 작성자
				String replyer=vo.getReplyer();
				
				// 로그인자가 작성자도 아니고 댓글단자도 아니면서 비밀댓글인 경우
				if(!userid.equals(writer) && 
						!userid.equals(replyer)){
					vo.setReplytext("비밀 댓글입니다.");
				}
			}
			
		}
		return items;
	}

	@Override
	public void create(ReplyVO vo) {
		replyDao.create(vo);		
	}

	@Override
	public void update(ReplyVO vo) {
		replyDao.update(vo);		
	}

	@Override
	public void delete(Integer rno) {
		replyDao.delete(rno);		
	}

	@Override
	public List<ReplyVO> json_list(Integer bno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(int bno) {
		return replyDao.count(bno);
	}

	@Override
	public ReplyVO detail(int rno) {
		return replyDao.detail(rno);
	}

}
