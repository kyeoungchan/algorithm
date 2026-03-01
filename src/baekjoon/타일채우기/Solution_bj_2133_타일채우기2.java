package baekjoon.타일채우기;

import java.io.*;
import java.util.*;

public class Solution_bj_2133_타일채우기2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        if (N % 2 == 1) {
            System.out.println(0);
        } else {
            int[] dp = new int[N + 1];
            dp[0] = 1;
            dp[2] = 3;
            for (int i = 4; i <= N; i += 2) {
                dp[i] = dp[i - 2] * dp[2];
                for (int j = 0; j < i - 2; j += 2) {
                    dp[i] += dp[j] * 2;
                }
            }
            System.out.println(dp[N]);
        }
        br.close();
    }
}
