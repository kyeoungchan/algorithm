//package a0207;

import java.util.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		boolean[][] isBlack = new boolean[100][100];
		int answer = 0;
		StringTokenizer st;
		for (int n = 0; n < N; n++) {
			answer += 100;
			st = new StringTokenizer(br.readLine(), " ");
			int startI = Integer.parseInt(st.nextToken());
			int startJ = Integer.parseInt(st.nextToken());
			for (int i = startI; i < 10 + startI; i++) {
				for (int j = startJ; j < 10 + startJ; j++) {
					if (!isBlack[i][j]) isBlack[i][j] = true;
					else answer--; // 한칸당 넓이는 1이다.
				}
			}
		}
		System.out.println(answer);
		br.close();
	}

}
