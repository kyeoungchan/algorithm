package baekjoon.퇴사2;

import java.io.*;
import java.util.*;

public class Solution_bj_15486_퇴사2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N + 1];
        // 50 50 50
        //    20
        //       100
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int t = Integer.parseInt(st.nextToken()); // 기간
            int p = Integer.parseInt(st.nextToken()); // 금액
            if (i + t - 1 > N) {
                if (dp[i] == 0)
                    dp[i] = dp[i - 1];
                continue;
            }
            dp[i + t - 1] = Math.max(dp[i - 1] + p, dp[i + t - 1]);
            if (dp[i] == 0) dp[i] = dp[i - 1];
        }
        int ans = 0;
        for (int i = 1; i < N + 1; i++) {
//            System.out.println(dp[i]);
            ans = Math.max(ans, dp[i]);
        }
        System.out.println(ans);
        br.close();
    }
}
