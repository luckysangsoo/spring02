package com.example.spring02.service.board;

public class Pager {
	// 페이지당 게시물 수
	public static final int PAGE_SCALE=10;
	// 화면 하단에 보여질 페이지 수
	public static final int BLOCK_SCALE=10;
	private int curPage;
	private int prevPage;
	private int nextPage;
	private int totPage;  // 전체 페이지 수
	private int totBlock; // 전체 페이지 블록 -- 
	private int curBlock;
	private int prevBlock;
	private int nextBlock;
	// where rn between #{start} and #{end}
	private int pageBegin;
	private int pageEnd;
	// 이전 41 42 43 44 45 46 47 48 49 50 다음
	// 이전 blockBegin 42 43 44 45 46 47 48 49 blockEnd 다음
	private int blockBegin; // 41
	private int blockEnd;   // 50
	
	//생성자
	// Pager(레코드 갯수, 현재 페이지 번호 )
	public Pager(int recordCount, int curPage) {
		curBlock=1;
		this.curPage=curPage;
		setTotPage(recordCount); // 전체 페이지 수 계산
		// between #{start} and #{end} 에 입력 될 값 계산
		setPageRange();          
		setTotBlock();   // 전체 페이지 블록 갯수 계산
		setBlockRange(); // 페이지 블럭의 시작, 끝 번호 계산 
	}
	
	public void setBlockRange(){
		// 현재 페이지가 몇번째 페이지 블럭에 속하는지 계산
		curBlock=(int)Math.ceil((curPage-1)/BLOCK_SCALE)+1;
		//현재 블럭의 시작,끝 번호 계산
		blockBegin=(curBlock-1)*BLOCK_SCALE+1;
		blockEnd=blockBegin+BLOCK_SCALE-1;
		// 마지막 블럭의 범위를 초과하지 않도록 처리
		if(blockEnd > totPage) blockEnd=totPage;
		// [이전] 을 눌렀을 때 이동할 페이지 번호
		prevPage=(curBlock==1)?1:(curBlock-1)*BLOCK_SCALE;
		// [다음] 을 눌렀을 때 이동할 페이지 번호
		nextPage=curBlock>totPage ? (curBlock*BLOCK_SCALE) : (curBlock*BLOCK_SCALE)+1;
		// 마지막 페이지가 범위를 초과하지 않도록 처리
		if(nextPage >= totPage) nextPage=totPage;
		
	}
	
	public void setPageRange(){
		//where rn between #{start} and #{end} 에 입력 될 값
		// 시작번호 = (현재페이지-1)*페이지당 게시물 수 + 1;
		pageBegin=(curPage-1)*PAGE_SCALE+1;
		// 끝번호 = 시작번호+페이지당 게시물 수 - 1;
		pageEnd=pageBegin + PAGE_SCALE-1;
	}
	
	
	//setter, getter
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextpage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getTotPage() {
		return totPage;
	}
	public void setTotPage(int recordCount) {
		// Math.ceil(실수) 실수 올림처리
		// 전체 게시물 수 : 991 개
		// 몇 페이지? 991 / 100 => 99.1 페이지
        // 그러나 마지막 1개의 게시물도 페이지로 처리해야 하니까 올림처리 한다
		// 결국, 100 페이지로 처리해줘야 한다.
		totPage = (int)Math.ceil(recordCount*1.0 / PAGE_SCALE );
	}
	public int getTotBlock() {
		return totBlock;
	}
	
	public void setTotBlock() {
		// Math.ceil(실수) 실수 올림처리
		// 페이지 처리와 같이 계산, 페이지 블럭의 갯수 계산(총 100페이지라면 10개 블럭 )
		totBlock = (int)Math.ceil(totPage / BLOCK_SCALE);
	}
	public int getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}
	public int getPrevBlock() {
		return prevBlock;
	}
	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}
	public int getNextBlock() {
		return nextBlock;
	}
	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}
	public int getPageBegin() {
		return pageBegin;
	}
	public void setPageBegin(int pageBegin) {
		this.pageBegin = pageBegin;
	}
	public int getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	public int getBlockBegin() {
		return blockBegin;
	}
	public void setBlockBegin(int blockBegin) {
		this.blockBegin = blockBegin;
	}
	public int getBlockEnd() {
		return blockEnd;
	}
	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}
	
}
