package edu.kh.project.test;

public class Calculator {

	public int add(int a, int b) {
		
		return a+b;
	}
	
	public int sub(int a, int b) {
		return a-b;
	}
	
	public int mult(int a, int b) {
		return a*b;
	}
	
	public int devi(int a, int b) {
		
		if ( b == 0) {
			throw new IllegalArgumentException("0으로 나눌 수 없음");
		}
		
		return a/b;
	}
	
	public boolean exam() {
		return 1==2;
	}
	
}
