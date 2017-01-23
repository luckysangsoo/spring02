package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.spring02.model.board.dao.BoardDAO;
import com.example.spring02.model.board.dto.BoardVO;


@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	BoardDAO boardDao;
	
	@Override
	public void create(BoardVO vo) throws Exception {
		//태그 문자 처리( < -> &lt, > -> &gt )
		String title = vo.getTitle();
		title = title.replace("<", "&lt");
		title = title.replace(">", "&gt");
		//replace(A,B) A를 B로 바꿈
		String writer = vo.getWriter();
		writer = writer.replace("<", "&lt");
		writer = writer.replace(">", "&gt");
		
		//공백처리 문자 처리
		title = title.replace("  ", "&nbsp;&nbsp;");
		writer = writer.replace("  ", "&nbsp;&nbsp;");
		//줄 바꿈 문자 처리
		String content=vo.getContent();
		content = content.replaceAll("\n", "<br>");
		vo.setContent(content);
				
		vo.setTitle(title);
		vo.setWriter(writer);
		
		boardDao.create(vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return boardDao.read(bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		boardDao.update(vo);
	}

	@Override
	public void delete(int bno) throws Exception {
		boardDao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll(int start, int end, String search_option, String keyword) throws Exception {
		return boardDao.listAll(start, end, search_option, keyword);
	}

	@Override
	public void increaseViewcnt(int bno, HttpSession session) throws Exception {
		long update_time=0;
		//세션에 저장된 조회시간 검색
		if(session.getAttribute("update_time" + bno) != null){
			update_time=(long)session.getAttribute("update_time" + bno);
		}
		// 시스템의 현재 시간
		long current_time = System.currentTimeMillis();
		// 일정시간이 경과 후 조회수 증가 처리
		if(current_time - update_time > 10*1000){
			boardDao.increaseViewcnt(bno);
			//열람시간을 세션에 저장한다.
			session.setAttribute("update_time" + bno, current_time);
		}
		
	}

	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		return boardDao.countArticle(search_option, keyword);
	}

}
