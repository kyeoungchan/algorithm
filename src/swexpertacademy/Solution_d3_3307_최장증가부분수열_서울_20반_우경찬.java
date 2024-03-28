package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d3_3307_최장증가부분수열_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d3_3307.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			int ANS = 1;
			int N = Integer.parseInt(br.readLine());
			int[] A = new int[N];
			int[] dp = new int[N];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				A[i] = Integer.parseInt(st.nextToken());
				dp[i] = 1;
			}
			for (int i = 0; i < N; i++) {
				int num = A[i];
				for (int j = 0; j < i; j++) {
					if (num >= A[j]) {
						dp[i] = Math.max(dp[i], dp[j] + 1);
						ANS = Math.max(ANS, dp[i]);
					}
				}
			}
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}
