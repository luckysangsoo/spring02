package com.example.spring02.model.board.dao;

import java.util.List;

import com.example.spring02.model.board.dto.BoardVO;

public interface BoardDAO {
	public void create(BoardVO vo) throws Exception;
	public BoardVO read(int bno) throws Exception;
	public void update(BoardVO vo) throws Exception;
	public void delete(int bno) throws Exception;
	public List<BoardVO> listAll(
			int start, int end,
			String search_option, String keyword) throws Exception;
	public void increaseViewcnt(int bno) throws Exception;
	//레코드 갯수 계산
	public int countArticle(String search_option, String keyword) throws Exception;

}
