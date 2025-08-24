package baekjoon.가장긴증가하는부분수열;

import java.io.*;
import java.util.*;

public class Solution_bj_11053_가장긴증가하는부분수열2 {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            int[] A  = new int[N];
            int[] dp = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }
            int answer = 1;
            dp[0]++;
            for (int i = 1; i < N; i++) {
                dp[i]++;
                for (int j = 0; j < i; j++) {
                    if (A[i] > A[j]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                        answer = Math.max(answer, dp[i]);
                    }
                }
            }
            System.out.println(answer);
        }
    }
}
