package com.example.spring02;

public class Test7 {

	public static void main(String[] args) throws Exception {
		String str="가나다라마바사";
		// 스트링을 바이트 배열로
		System.out.println(str.getBytes());
		System.out.println(new String(str.getBytes("utf-8"),"iso-8859-1")); // 서유럽언어
		System.out.println(new String(str.getBytes("utf-8"),"ms949")); // 한국어
		System.out.println("1 -------------");
		System.out.println(new String(str.getBytes("ms949"),"iso-8859-1")); // 서유럽언어
		System.out.println(new String(str.getBytes("utf-8"),"iso-8859-1")); // 한국어
		
		System.out.println("2 -------------");
		System.out.println(new String(str.getBytes("iso-8859-1"),"ms949")); // 서유럽언어
		System.out.println(new String(str.getBytes("iso-8859-1"),"utf-8")); // 한국어
		
		System.out.println("3 -------------");
		System.out.println(new String(str.getBytes("ms949"),"ms949")); // 서유럽언어
		System.out.println(new String(str.getBytes("iso-8859-1"),"utf-8")); // 한국어
		
		
		// new String(fileName.getBytes("utf-8"),"iso-8859-1") + "\"");
	}

}
