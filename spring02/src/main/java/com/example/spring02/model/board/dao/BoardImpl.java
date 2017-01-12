package com.example.spring02.model.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.example.spring02.model.board.dto.BoardVO;

@Repository
public class BoardImpl implements BoardDAO {

	@Inject
	SqlSession sqlSession;
	
	@Override
	public void create(BoardVO vo) throws Exception {
		sqlSession.insert("board.insert", vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return sqlSession.selectOne("board.view", bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update("board.updateArticle", vo);
	}

	@Override
	public void delete(int bno) throws Exception {

	}

	@Override
	public List<BoardVO> listAll(int start, int end, String search_option, String keyword) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		//sql between #{start} and #{end} 들어갈 조건
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("board.listAll", map);
	}

	@Override
	public void increaseViewcnt(int bno) throws Exception {
		sqlSession.update("board.increaseCount", bno);	
	}

	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		return sqlSession.selectOne("board.countArticle", map);
	}

}
