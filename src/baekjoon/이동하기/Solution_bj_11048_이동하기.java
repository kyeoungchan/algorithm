package baekjoon.이동하기;

import java.util.*;
import java.io.*;

public class Solution_bj_11048_이동하기 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                dp[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i == 0 && j == 0) continue;
                else if (i > 0 && j > 0) {
                    dp[i][j] += Math.max(dp[i - 1][j - 1], Math.max(dp[i - 1][j], dp[i][j - 1]));
                } else if (i == 0) {
                    dp[i][j] += dp[i][j - 1];
                } else {
                    dp[i][j] += dp[i - 1][j];
                }
            }
        }
        System.out.println(dp[N - 1][M - 1]);
        br.close();
    }
}
