package baekjoon.외판원순회2;

import java.util.*;
import java.io.*;

public class Solution_bj_10971_외판원순회2_서울_20반_우경찬 {
	
	static int N, result;
	static int[][] W;
	static boolean[] v;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		W = new int[N + 1][N + 1];
		for(int i = 1; i < N + 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j < N + 1; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		result = Integer.MAX_VALUE;
		v = new boolean[N + 1];
		for (int i = 1; i < N + 1; i++) {
			dfs(i, i, 0, 0);
		}
		System.out.println(result);
		br.close();
	}

	static void dfs(int start, int from, int cnt, int totalCost) {
		if (cnt == N - 1) {
			// 마지막 원소는 더이상 갈 곳이 start 말고는 없으므로 cnt==N이 아니라 cnt==N-1인 경우에 start로 가야한다.
			if (W[from][start] != 0)
				result = Math.min(result, totalCost + W[from][start]);
			return;
		}
		
		
		v[from] = true;
		for (int i = 1; i < N + 1; i++) {
			if (!v[i] && W[from][i] != 0) {
				dfs(start, i, cnt + 1, totalCost + W[from][i]);
			}
		}
		v[from] = false;
	}

}
