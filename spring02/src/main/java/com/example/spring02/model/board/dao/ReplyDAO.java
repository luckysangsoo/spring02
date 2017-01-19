package com.example.spring02.model.board.dao;

import java.util.List;

import com.example.spring02.model.board.dto.ReplyVO;

public interface ReplyDAO {
	public List<ReplyVO> list(Integer bno,int start,int end);
	public void create(ReplyVO vo);
	public int count(int bno);
	public void update(ReplyVO vo);
	public void delete(Integer rno);
	public ReplyVO detail(int rno);

}
