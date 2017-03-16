package com.example.spring02.controller.board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.BoardVO;
import com.example.spring02.service.board.BoardService;
import com.example.spring02.service.board.Pager;
import com.example.spring02.service.board.ReplyService;

@Controller   // 현재 클래스를 컨트롤러빈(bean)으로 등록
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 의존관계 주입 => BoardServiceImpl 생성
	// Ioc 의존관계 역전
	@Inject
	BoardService boardService;
	@Inject
	ReplyService replyService;
	
	
	@RequestMapping("board/list.do") //  /board/list.do
	public ModelAndView list(
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(defaultValue="all") String search_option, 
			@RequestParam(defaultValue="") String keyword) throws Exception {
		// 레코드 갯수 계산
		int count = boardService.countArticle(search_option, keyword);
		// 페이지 나누기 관련 처리
		Pager pager=new Pager(count, curPage);
		int start=pager.getPageBegin();
		int end=pager.getPageEnd();
			
		
		List<BoardVO> list= boardService.listAll(start, end, search_option, keyword);
		// 모델과 뷰
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("pager", pager);
		mav.addObject("map", map); //데이터 저장
				
		/*mav.addObject("list", list);
		mav.addObject("count", count);
		//검색 조건의 필터 창 항목이 풀리는 경우를 대비해서 검색 필터와 keyword를 다시 보내줘야 한다. 
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);*/
		logger.info("board list mav"+mav);
		return mav; //list.jsp로 
	}

	//@RequestMapping("write.do")
	//value="url이름", method="전송방식"
	@RequestMapping(value="board/write.do", method=RequestMethod.GET)
	public String write() throws Exception {
		return "board/write"; //write.jsp로 이동
	}
	
	@RequestMapping(value="board/insert.do", method=RequestMethod.POST)
	public String insert(@ModelAttribute BoardVO vo,
			HttpSession session) throws Exception {
		//세션에 저장된 아이디를 조회 가져와서 저장
		String writer = (String)session.getAttribute("userid");
		vo.setWriter(writer);
		boardService.create(vo);
		return "redirect:/board/list.do";
	}
	//public String insert(HttpSeHttpServletRequest request){
	//	
	//}
	
	//public ModelAndView view(HttpServletRequest request){
	// int bno=Integer.parseInt(request.getParameter("bno"));
	//@ModelAttribute 객체로 저장됨
	@RequestMapping(value="board/view.do", method=RequestMethod.GET)
	public ModelAndView view(
			@RequestParam int bno,
		    @RequestParam int curPage,
		    @RequestParam String search_option,
		    @RequestParam String keyword,
			HttpSession session) throws Exception {
		//조회수 증가 처리
		boardService.increaseViewcnt(bno, session);
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("dto", boardService.read(bno));
		mav.addObject("count", replyService.count(bno));
		mav.addObject("curPage", curPage);
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);
		mav.setViewName("board/view"); // board/view.jsp
		logger.info("mav"+mav);
		return mav;
	}
	
	//폼에서 입력한 내용들은 @ModelAttribute BoardVO vo 로 전달 됨.
	@RequestMapping(value="board/update.do", method=RequestMethod.POST)
	public String update(@ModelAttribute BoardVO vo) throws Exception {
		boardService.update(vo);
		//model.addAttribute("list", boardService.listAll());  
		return "redirect:/board/list.do";
	}
	
	@RequestMapping(value="board/delete.do", method=RequestMethod.POST)
	public String delete(@RequestParam int bno) throws Exception {
		//삭제 처리
		boardService.delete(bno);
		//게시물 이동
		return "redirect:/board/list.do";
	}
	
	@RequestMapping(value="board/imageUpload.do", method=RequestMethod.POST)
	public void imageUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile upload) throws Exception {
		// http header 설정
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset-utf-8");
		
		logger.info("imageUpload................");
		
		OutputStream out=null; // java.io, 서버로 파일을 올릴 떄는 OutputStream
		PrintWriter printWriter=null; // java.io
		// 업로드한 파일 이름
		String fileName=upload.getOriginalFilename();
		//logger.info(fileName);
		
		byte[] bytes=upload.getBytes();
		// 배포경로를 적어줘야합니다.
		// String uploadPath = "업로드 디렉토리";
		// 배포경로 찾는 방법
		// Servers -> 마우스 오른쪽 버튼 클릭 -> Browse Deployment Location
		// E:\workspace-sts-3.8.2.RELEASE\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\spring02\WEB-INF\views\images
		// 배포 디렉토리
		
		String uploadPath = 
		"E:\\workspace-sts-3.8.2.RELEASE\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\" + fileName;
		logger.info(uploadPath);
		
		out=new FileOutputStream(new File(uploadPath)); // java.io
		// 서버에 저장됨.
		out.write(bytes);
		
		String callback=request.getParameter("CKEditorFuncNum");
		printWriter=response.getWriter();
			
		String fileUrl=request.getContextPath()+"/images/"+fileName;
		logger.info("request.getContextPath()================" + request.getContextPath());
		logger.info("fileUrl============= : " + fileUrl);
		
		printWriter.println(
		"<script>window.parent.CKEDITOR.tools.callFunction("
			+ callback+",'"+fileUrl+"','이미지가 업로드되었습니다.')"
			+ "</script>");
		//스트림 닫기
		printWriter.flush();
	}
}
