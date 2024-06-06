package baekjoon.동전2;

import java.io.*;
import java.util.*;

public class Solution_bj_2294_동전2 {

    static final int INF = 987654321;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] coins = new int[n];
        for (int i = 0; i < n; i++)
            coins[i] = Integer.parseInt(br.readLine());
        int[] dp = new int[k + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 1; i < k + 1; i++) {
            for (int coin : coins) {
                if (i - coin < 0) continue;
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        if (dp[k] == INF) dp[k] = -1;
        System.out.println(dp[k]);
        br.close();
    }
}
