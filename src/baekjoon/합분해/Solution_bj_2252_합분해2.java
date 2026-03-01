package baekjoon.합분해;

import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/2225
 */
public class Solution_bj_2252_합분해2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int mod = 1_000_000_000;
        int[][] dp = new int[N + 1][K + 1];

        for (int i = 0; i <= K; i++) {
            dp[0][i] = 1;
        }

        /* N=1
         * 1                               K=1
         * 1+0     0+1                     K=2
         * 1+0+0   0+1+0   0+0+1           K=3
         * 1+0+0+0 0+1+0+0 0+0+1+0 0+0+0+1 K=4
         *
         * N=2
         * 2                                                                               K=1
         * 2+0     1+1     0+2                                                             K=2
         * 2+0+0   1+1+0   0+2+0   1+0+1   0+1+1   0+0+2                                   K=3
         * 2+0+0+0 1+1+0+0 0+2+0+0 1+0+1+0 0+1+1+0 0+0+2+0 1+0+0+1 0+1+0+1 0+0+1+1 0+0+0+2 K=4
         *
         * N=3
         * 3                                                           K=1
         * 3+0   2+1   1+2   0+3                                       K=2
         * 3+0+0 2+1+0 1+2+0 0+3+0 2+0+1 1+1+1 0+2+1 1+0+2 0+1+2 0+0+3 K=3*/
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= K; j++) {
                for (int k = 0; k <= i; k++) {
                    dp[i][j] += dp[k][j - 1];
                    dp[i][j] %= mod;
                }
            }
        }

/*
        // 이게 더 좋은 방식이지만 위 방식으로도 성공
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= K; j++) {
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod;
            }
        }
*/

        System.out.println(dp[N][K]);

        br.close();
    }
}
