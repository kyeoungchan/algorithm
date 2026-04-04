package baekjoon.행렬곱셈순서;

import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/11049
 * NxM 행렬 A와 MxK 행렬 B를 곱할 때 곱셈 연산의 수는 총 NxMxK
 * 곱셈의 순서에 따라 곱셈 연산의 수가 달라진다.
 * 곱셈 연산 횟수의 최솟값을 구하는 프로그램
 */
public class Solution_bj_11049_행렬곱셈순서 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[][] matrix = new int[N][2];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            matrix[i][0] = Integer.parseInt(st.nextToken());
            matrix[i][1] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[N][N];


        /* 예를 들어, A의 크기가 5×3이고, B의 크기가 3×2, C의 크기가 2×6인 경우에 행렬의 곱 ABC를 구하는 경우를 생각해보자.
        AB를 먼저 곱하고 C를 곱하는 경우 (AB)C에 필요한 곱셈 연산의 수는 5×3×2 + 5×2×6 = 30 + 60 = 90번이다.
        BC를 먼저 곱하고 A를 곱하는 경우 A(BC)에 필요한 곱셈 연산의 수는 3×2×6 + 5×3×6 = 36 + 90 = 126번이다.*/

        /* A: 5x4, B: 4x3, C: 3x2, D: 2x1
         * AxBxCxD     = 5*4*3 + 5*3*2 + 5*2*1 = 100
         * AxBx(CxD)   = 5*4*3 + 3*2*1 + 5*3*1 = 81
         * Ax(BxC)xD   = 4*3*2 + 5*4*2 + 5*2*1 = 74
         * Ax((BxC)xD) = 4*3*2 + 4*2*1 + 5*4*1 = 52
         * Ax(Bx(CxD)) = 3*2*1 + 4*3*1 + 5*4*1 = 38 */

        for (int d = 1; d < N; d++) {
            for (int s = 0; s < N - d; s++) {
                int e = s + d;
                if (d == 1) {
                    int A = matrix[s][0];
                    int B = matrix[s][1];
                    int C = matrix[e][1];
                    dp[s][e] = A * B * C;
                } else {

                    int result = Integer.MAX_VALUE;

                    for (int d2 = 0; d2 < d; d2++) {
                        int A = matrix[s][0];
                        int B = matrix[s + d2][1];
                        int C = matrix[e][1];
                        int ops = A * B * C;

                        ops += dp[s][s + d2];
                        ops += dp[s + d2 + 1][e];

                        result = Math.min(result, ops);
                    }

                    dp[s][e] = result;
                }
            }
        }

/*
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
*/
        System.out.println(dp[0][N - 1]);

        br.close();
    }
}
