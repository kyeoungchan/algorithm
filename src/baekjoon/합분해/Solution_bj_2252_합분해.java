package baekjoon.합분해;

import java.io.*;
import java.util.*;

/**
 * 0부터 N까지의 정수 K개를 더해서 그 합이 N이 되는 경우의 수를 구하는 프로그램
 * https://www.acmicpc.net/problem/2225
 */
public class Solution_bj_2252_합분해 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[][] dp = new int[N + 1][K + 1];
        int mod = 1_000_000_000;

        // 0은 몇 개를 더하든 0이 되는 한가지의 경우밖에 없다.
        for (int i = 0; i <= K; i++) {
            dp[0][i] = 1;
        }

        /*
        * N=1
        * - K=1 1
        * - K=2 0+1 1+0
        * - K=3 0+0+1 0+1+0 1+0+0
        *
        * N=2
        * - K=1 2
        * - K=2 0+2 1+1 2+0
        * - k=3 0+0+2 0+1+1 0+2+0 1+0+1 1+1+0 2+0+0
        *
        * N=3
        * - K=1 3
        * - K=2 0+3 1+2 2+1 3+0
        * - K=3 0+0+3 0+1+2 0+2+1 0+3+0 1+0+2 1+1+1 1+2+0 2+0+1 2+1+0 3+0+0
        *
        * dp[N][K] = N을 이루는 K-1의 경우에 0+를 붙인 경우의 수 + N-1을 이루는 K의 경우에 제일 앞 수에 1을 더한 경우의 수
        *          = dp[N][K-1] + dp[N-1][K]*/
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= K; j++) {
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod;
            }
        }

        System.out.println(dp[N][K]);

/*
        for (int i = 0; i < N + 1; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
*/
        br.close();
    }
}
