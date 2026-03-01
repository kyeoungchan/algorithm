package baekjoon.타일채우기;

import java.io.*;
import java.util.*;

/**
 * 3XN 크기의 벽을 2X1, 1X2 크기의 타일로 채우는 경우의 수를 구해보자.
 * https://www.acmicpc.net/problem/2133
 * 참고: https://eunchaan.tistory.com/51
 */
public class Solution_bj_2133_타일채우기 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];
        if (n % 2 == 1) {
            System.out.println(0);
        } else if (n >= 2) {
            dp[0] = 1;
            dp[2] = 3;
            for (int i = 4; i <= n; i += 2) {
                // 우측에 새롭게 dp[2]를 붙인 경우의 수.
                dp[i] = dp[i - 2] * dp[2];
                for (int j = i - 4; j > 0; j -= 2) {
                    /* 우측 2줄에 새로운 타일을 붙이는 경우의 수가 아니라
                    * 우측 4줄이 서로 연결되어 있는 경우의 수(2개)
                    * 우측 6줄이 서로 연결되어 있는 경우의 수(2개)
                    * ...
                    * 다른 블로그에서는 이 경우를 특별한 경우라고 지칭하고들 있다. */
                    dp[i] += dp[j] * 2;
                }
                // 마지막으로 모든 줄이 연결되어 있는 경우의 수(2개)
                dp[i] += 2;
            }
            System.out.println(dp[n]);
        }
        br.close();
    }
}
