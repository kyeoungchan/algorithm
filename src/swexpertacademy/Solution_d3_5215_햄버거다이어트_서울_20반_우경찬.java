package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d3_5215_햄버거다이어트_서울_20반_우경찬 {

	static final int FLAVOR_IDX = 0, CALORIES_IDX = 1;
	static int N, L, MAX;
	static int[][] hamburgerInfo;
	static boolean[] inputted;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d3_5215.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken()); // 재료의 수
			L = Integer.parseInt(st.nextToken()); // 칼로리 제한
			MAX = 0;
			hamburgerInfo = new int[N][2];
			inputted = new boolean[N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				hamburgerInfo[i][FLAVOR_IDX] = Integer.parseInt(st.nextToken());
				hamburgerInfo[i][CALORIES_IDX] = Integer.parseInt(st.nextToken());
			}

			subset(0);

			sb.append("#").append(tc).append(" ").append(MAX).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void subset(int cnt) {
		if (cnt == N) {
			int totalCal = 0;
			int totalFla = 0;
			for (int i = 0; i < N; i++) {
				if (inputted[i]) {
					totalCal += hamburgerInfo[i][CALORIES_IDX];
					totalFla += hamburgerInfo[i][FLAVOR_IDX];
				}
			}
			if (totalCal <= L)
				MAX = Math.max(MAX, totalFla);
			return;
		}

		inputted[cnt] = true;
		subset(cnt + 1);
		inputted[cnt] = false;
		subset(cnt + 1);
	}

}
