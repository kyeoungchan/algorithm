package baekjoon;

import java.util.*;
import java.io.*;

public class Solution_bj_4485_녹색옷입은애가젤다지_서울_20반_우경찬 {
	
	static int N, map[][], dp[][], di[] = {-1, 0, 1, 0}, dj[] = {0, 1, 0, -1};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int tc = 0;
		int ANS=0;
		while(true) {
			ANS = 0;
			tc++;
			N = Integer.parseInt(br.readLine());
			if (N == 0) {
				break;
			}
			map = new int[N][N];
			dp = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					dp[i][j] = Integer.MAX_VALUE;
				}
			}
			ArrayDeque<int[]> q = new ArrayDeque<>();
			q.offer(new int[] {0,0});
			dp[0][0] = map[0][0];

			while (!q.isEmpty()) {
				int[] cur = q.poll();
				int ci = cur[0];
				int cj = cur[1];
				for (int d = 0; d < 4; d++) {
					int ni = ci + di[d];
					int nj = cj + dj[d];
					if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1) continue;
					if (dp[ni][nj] > dp[ci][cj] + map[ni][nj]) {
						dp[ni][nj] = dp[ci][cj] + map[ni][nj];
						q.offer(new int[] {ni, nj});
					}
				}
				
			}
			ANS = dp[N-1][N-1];
			sb.append("Problem ").append(tc).append(": ").append(ANS).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}
