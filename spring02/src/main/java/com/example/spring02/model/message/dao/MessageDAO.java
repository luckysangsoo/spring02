package com.example.spring02.model.message.dao;

import com.example.spring02.model.message.dto.MessageVO;

public interface MessageDAO {
	public void create(MessageVO vo);
	public MessageVO readMessage(int mid);
	public void updateState(int mid);

}
