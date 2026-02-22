package baekjoon.전깃줄;

import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/2565
 */
public class Solution_bj_2565_전깃줄2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[][] wires = new int[n][2];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            wires[i][0] = Integer.parseInt(st.nextToken());
            wires[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(wires, Comparator.comparing(wire -> wire[0]));

        int[] dp = new int[n];
        int max = 0;

        for (int i = 0; i < n; i++) {
            // 최소 하나는 설치 가능하다.
            dp[i]++;
            for (int j = 0; j < i; j++) {
                if (wires[j][1] < wires[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    max = Math.max(max, dp[i]);
                }
            }
        }

        System.out.println(n - max);
        br.close();
    }
}
