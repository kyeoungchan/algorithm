package baekjoon.계단오르기;

import java.io.*;
import java.util.*;

public class Solution_bj_2579_계단오르기2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n + 1];
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        dp[1] = arr[1];
        if (n > 1) {
            dp[2] = arr[1] + arr[2];
        }

        for (int i = 3; i <= n; i++) {
            // 무조건 현재 계단을 밟는다.
            // 현재 계단을 밟기전 2칸 전을 밟은 상태일지, 3칸 전과 1칸 전을 밟은 상태일지 구하면 된다.
            dp[i] = Math.max(dp[i - 2], dp[i - 3] + arr[i - 1]) + arr[i];
        }

        System.out.println(dp[n]);

        br.close();
    }
}
