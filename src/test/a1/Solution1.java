package test.a1;

import java.io.*;
import java.util.*;

class Solution1 {
	
	static int N, M, ANS, gI, gJ;
	static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
	static int[][] map, count;
	static boolean[][][] v;
	
	public static void main(String args[]) throws Exception	{
		System.setIn(new FileInputStream("res/input1.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N][M];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if (map[i][j] == 3) {
						gI = i;
						gJ = j;
					}
				}
			}
			int startI = N - 1;
			int startJ = 0;
			ANS = Integer.MAX_VALUE;
			v = new boolean[N][M][N];
			count = new int[N][M];

			v[startI][startJ][0] = true;
			bfs();
//			dfs(startI, startJ, 0);
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		System.out.println(sb.toString());
		br.close();		
	}
	
	static void bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {N - 1, 0, 0});
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int i = cur[0];
			int j = cur[1];
			int hardScore = cur[2];
			if (hardScore > ANS) continue;
			if (i == gI && j == gJ) {
				ANS = Math.min(ANS, hardScore);
				continue;
			}
			
			for (int d = 0; d < 4; d++) {
				if (d % 2 == 0) {
					// 상하
					int k = 1;
					while (true) {
						int ni = i + di[d] * k;
						int nHardScore = Math.max(hardScore, k);
						if (ni < 0 || ni >= N || v[ni][j][nHardScore]) break;
						if (map[ni][j] != 0) {
							v[ni][j][nHardScore] = true;
							q.offer(new int[] {ni, j, nHardScore});
							break;
						}
						k++;
					}
				} else {
					// 좌우
					int nj = j + dj[d];
					if (nj < 0 || nj >= M || map[i][nj] == 0 || v[i][nj][hardScore]) continue;
					v[i][nj][hardScore] = true;
					q.offer(new int[] {i, nj, hardScore});
				}
				
			}
		}
	}
	
}