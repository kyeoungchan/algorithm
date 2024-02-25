package example;

import java.io.*;
import java.util.*;

public class CombMain {
	static int N = 4, R = 3, C = 0;
	static int[] a = {1, 2, 3, 4}, b = new int[R];
	static boolean[] v = new boolean[N];
	
	static void comb(int cnt, int start) {
		if (cnt == R) {
			System.out.println(Arrays.toString(b));
			C++;
			return;
		}
		
		for (int i = start; i < N; i++) {
			if (v[i]) continue;
			b[cnt] = a[i];
			comb(cnt + 1, i + 1);
//			comb(cnt + 1, i); // 중복 조합
		}
	}
	
	public static void main(String[] args) {
		C = 0;
		comb(0, 0); // 4C3 조합(Combination): 순서무관(123==321) 중복불가
		System.out.println(C);
	}
}
