package baekjoon.평범한배낭;

import java.io.*;
import java.util.*;

public class Solution_bj_12865_평범한배낭3 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[][] items = new int[N][2];
            int[] dp = new int[K + 1];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int W = Integer.parseInt(st.nextToken());
                int V = Integer.parseInt(st.nextToken());
                items[i][0] = W; // 무게
                items[i][1] = V; // 가치
            }

            for (int i = 0; i < N; i++) {
                int W = items[i][0];
                int V = items[i][1];
                for (int j = K; j >= W; j--) {
                    dp[j] = Math.max(dp[j], dp[j - W] + V);
                }
            }
            System.out.println(dp[K]);
        }
    }
}
