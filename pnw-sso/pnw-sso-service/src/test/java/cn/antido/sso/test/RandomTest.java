package cn.antido.sso.test;

import java.util.Random;

public class RandomTest {
public static void main(String[] args) {
	Random random = new Random();
	for (int i = 0; i < 20; i++) {
		System.out.println((int)((Math.random()*9+1)*100000) + "");
	}
}
}
