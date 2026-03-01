package baekjoon.one23더하기;

import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/9095
 */
public class Solution_bj_9095_123더하기2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        /* 1
         * 1+1     2
         * 1+1+1   2+1   1+2   3
         * 1+1+1+1 2+1+1 1+2+1 3+1 1+1+2 2+2 1+3*/
        int[] dp = new int[11];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        for (int i = 4; i < 11; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            int n =  Integer.parseInt(br.readLine());
            sb.append(dp[n]).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
