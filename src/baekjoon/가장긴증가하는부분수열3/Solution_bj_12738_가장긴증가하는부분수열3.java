package baekjoon.가장긴증가하는부분수열3;

import java.io.*;
import java.util.*;

public class Solution_bj_12738_가장긴증가하는부분수열3 {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            int[] A = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            int length = 0;
            int[] dp = new int[N];
            dp[length++] = A[0];
            for (int i = 1; i < N; i++) {
                if (A[i] > dp[length - 1]) {
                    dp[length++] = A[i];
                } else if (A[i] < dp[length - 1]) {
                    int left = 0;
                    int right = length - 1;
                    while (left <= right) {
                        int mid = left + (right - left) / 2;
                        if (A[i] > dp[mid]) {
                            left = mid + 1;
                        } else {
                            right = mid - 1;
                        }
                    }
                    dp[left] = A[i];
                }
            }
            System.out.println(length);
        }
    }
}
