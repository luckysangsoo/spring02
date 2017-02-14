package com.example.spring02.service.shop;

import java.util.List;

import com.example.spring02.model.shop.dto.CartVO;

public interface CartService {
	public void insert(CartVO vo);
	public List<CartVO> listCart(String userid);
	public void delete(int cart_id);
	public void update(int cart_id);
	public int sumMoney(String userid);
}
