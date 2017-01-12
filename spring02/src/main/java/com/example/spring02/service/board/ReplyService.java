package com.example.spring02.service.board;

import java.util.List;
import com.example.spring02.model.board.dto.ReplyVO;

public interface ReplyService {
	public List<ReplyVO> list(Integer bno);
	public void create(ReplyVO vo);
	public void update(ReplyVO vo);
	public void delete(Integer rno);

}
