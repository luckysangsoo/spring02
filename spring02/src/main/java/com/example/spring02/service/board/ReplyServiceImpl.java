package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.spring02.model.board.dao.ReplyDAO;
import com.example.spring02.model.board.dto.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	ReplyDAO replyDao; 
	
	@Override
	public List<ReplyVO> list(Integer bno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ReplyVO vo) {
		replyDao.create(vo);
		
	}

	@Override
	public void update(ReplyVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer rno) {
		// TODO Auto-generated method stub
		
	}

	

}
