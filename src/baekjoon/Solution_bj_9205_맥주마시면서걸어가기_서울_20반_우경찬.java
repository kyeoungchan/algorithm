package baekjoon;

import java.io.*;
import java.util.*;

public class Solution_bj_9205_맥주마시면서걸어가기_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		for (int tc = 0; tc < t; tc++) {
			int N = Integer.parseInt(br.readLine());
			int[][] positions = new int[N + 2][2];
			for (int i = 0; i < N + 2; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				positions[i][0] = Integer.parseInt(st.nextToken());
				positions[i][1] = Integer.parseInt(st.nextToken());
			}
			
			int[][] g = new int[N+2][N+2];
			for (int i = 0; i < N + 2; i++) {
				for (int j = 0; j < N + 2; j++) {
					if (i == j) continue;
					g[i][j] = Math.abs(positions[i][0] - positions[j][0]) + Math.abs(positions[i][1] - positions[j][1]);
				}
			}
			String ans = null;
			boolean[] happy = new boolean[N+2];
			happy[0] = true;
			for (int k = 0; k < N + 2; k++) {
				for (int i = 0; i < N + 2; i++) {
					for (int j = 0; j < N + 2; j++) {
						g[i][j] = Math.min(g[i][j], g[i][k] + g[k][j]);
						if (happy[i] && g[i][j] <= 1000) happy[j] = true;
					}
				}
			}
			if (happy[N+1]) ans = "happy";
			else ans = "sad";
			sb.append(ans).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}
