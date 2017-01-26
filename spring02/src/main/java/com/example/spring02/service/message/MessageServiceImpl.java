package com.example.spring02.service.message;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.spring02.model.message.dao.MessageDAO;
import com.example.spring02.model.message.dao.PointDAO;
import com.example.spring02.model.message.dto.MessageVO;

//현재 클래스를 스프링에서 관리하는 bean으로
@Service 
public class MessageServiceImpl implements MessageService {

	@Inject
	MessageDAO messageDao;
	
	@Inject
	PointDAO pointDao;
	
	@Override
	public void addMessage(MessageVO vo) {
		// 메세지를 테이블에 저장
		messageDao.create(vo);
		// 메세지를 발송한 회원에게 10포인트 추가
		pointDao.updatePoint(vo.getSender(), 10);
		
	}

	@Override
	public MessageVO readMessage(String userid, int mid) {
		// TODO Auto-generated method stub
		return null;
	}

}
