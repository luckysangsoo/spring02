package com.example.spring02.model.message.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring02.model.message.dto.MessageVO;

@Repository
public class MessageImpl implements MessageDAO {

	@Inject
	SqlSession sqlSession;
	
	@Override
	public void create(MessageVO vo) {
		sqlSession.insert("message.create", vo);		
	}

	@Override
	public MessageVO readMessage(int mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateState(int mid) {
		// TODO Auto-generated method stub
		
	}

}
