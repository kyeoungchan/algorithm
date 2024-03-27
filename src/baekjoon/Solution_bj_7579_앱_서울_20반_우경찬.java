package baekjoon;

import java.util.*;
import java.io.*;

/**
 * 너무 어렵게 생각하지 말고 비용 내에서 최대 메모리를 할당받을 수 있는 앱을 선택하는 것으로 생각하자.
 */
public class Solution_bj_7579_앱_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[] A = new int[N + 1]; // 각 앱들마다 사용하고 있는 메모리
		int[] C = new int[N + 1]; // 각 앱들마다 비활성화했을 때의 비용
		
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < N + 1; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		int maxCost = 0;
		
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i < N + 1; i++) {
			C[i] = Integer.parseInt(st.nextToken());
			maxCost += C[i];
		}
		
		int[][] dp = new int[N + 1][maxCost + 1];
		
		int ANS = Integer.MAX_VALUE;
		for (int i = 1; i < N + 1; i++) {
			// 1~N번 앱을 선택
			int mi = A[i];
			int ci = C[i];
			for (int c = 0; c < maxCost + 1; c++) {
				// 비용이 0인 앱들도 존재할 수 있으므로 0부터 체킹을 해야한다!
				if (ci > c) {
					dp[i][c] = dp[i-1][c]; 
				} else {
					dp[i][c] = Math.max(dp[i-1][c], dp[i-1][c-ci] + mi);
					if (dp[i][c] >= M) {
						// 비용이 업데이트 됐을 때 M 이상의 메모리를 할당 받은 경우 최소 비용을 업데이트한다.
						ANS = Math.min(ANS, c);
					}
				}
			}
		}
		System.out.println(ANS);
		br.close();
	}

}
 