package com.example.spring02;

public class Test6 {

	public static void main(String[] args) {
		String fileName="a352e1ee-da27-4898-a508-509c51dfc60d_20170119_171308.png";
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		System.out.println(formatName);
	}

}
