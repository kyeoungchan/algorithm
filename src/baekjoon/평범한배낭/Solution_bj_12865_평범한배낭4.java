package baekjoon.평범한배낭;

import java.io.*;
import java.util.*;

public class Solution_bj_12865_평범한배낭4 {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[] dp = new int[K + 1];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int W = Integer.parseInt(st.nextToken());
                int V = Integer.parseInt(st.nextToken());
                for (int w = K; w >= W; w--) {
                    dp[w] = Math.max(dp[w], dp[w - W] + V);
                }
            }
            System.out.println(dp[K]);
        }
    }
}
