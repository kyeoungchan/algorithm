package baekjoon.연속합;

import java.util.*;
import java.io.*;

/**
 * n개의 정수로 이루어진 임의의 수열이 주어진다.
 * 이 중 연속된 몇 개의 수를 선택해서 구할 수 있는 합 중 가장 큰 합
 */
public class Solution_bj_1912_연속합 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // (1 ≤ n ≤ 100,000)
        int n = Integer.parseInt(br.readLine());

        int max = 0;

        int[] dp = new int[n];
        int[] arr = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        max = arr[0] = dp[0] = Integer.parseInt(st.nextToken());

        for (int i = 1; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            dp[i] = Math.max(arr[i], dp[i - 1] + arr[i]);
            max = Math.max(max, dp[i]);
        }

        System.out.println(max);

        br.close();
    }
}
