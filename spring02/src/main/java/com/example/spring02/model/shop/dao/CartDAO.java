package com.example.spring02.model.shop.dao;

import java.util.List;

import com.example.spring02.model.shop.dto.CartVO;

public interface CartDAO {
	public void insert(CartVO vo);
	public List<CartVO> listCart(String userid);
	void delete(int cart_id);
	void update(int cart_id);
	public int sumMoney(String userid);

}
