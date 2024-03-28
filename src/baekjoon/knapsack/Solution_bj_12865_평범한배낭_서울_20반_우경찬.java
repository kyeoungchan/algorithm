package baekjoon.knapsack;

import java.util.*;
import java.io.*;

public class Solution_bj_12865_평범한배낭_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[][] items = new int[N][2];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			items[i][0] = Integer.parseInt(st.nextToken());
			items[i][1] = Integer.parseInt(st.nextToken());
		}
		int[] dp = new int[K + 1];
		for (int i = 0; i < N; i++) {
			int wi = items[i][0];
			int vi = items[i][1];
			for (int w = K; w > wi - 1; w--) {
				dp[w] = Math.max(dp[w - wi] + vi, dp[w]);
			}
		}
		System.out.println(dp[K]);
/*
		int[][] dp = new int[N][K + 1];

		int tempW = items[0][0];
		int tempV = items[0][1];

		for (int w = 1; w < K + 1; w++) {
			if (tempW <= w) {
				dp[0][w] = tempV;
			}
		}

		for (int i = 1; i < N; i++) {
			// 아이템 선택
			int wi = items[i][0];
			int vi = items[i][1];
			for (int w = 1; w < K + 1; w++) {
				if (wi > w) {
					dp[i][w] = dp[i-1][w];
				} else {
					dp[i][w] = Math.max(dp[i-1][w-wi] + vi, dp[i-1][w]);
				}
			}
		}
		System.out.println(dp[N-1][K]);
*/
		br.close();
	}

}
